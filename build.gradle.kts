import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.3"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	id("dev.projektor.publish") version "8.0.0"
	application
	jacoco
}
apply(plugin = "idea")
apply(plugin = "java")
apply(plugin = "io.spring.dependency-management")

group = "com.gourav"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

application {
	mainClass.set("com.gourav.ApplicationKt")
}
repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// kotlin logging
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
}

tasks.withType<Test> {
	finalizedBy(tasks.jacocoTestReport)
	useJUnitPlatform()
}
jacoco {
	applyTo(tasks.run.get())
}
projektor {
	serverUrl = "https://localhost:8080"
	publishRetryMaxAttempts = 3
	publishRetryInterval = 100
	publishTimeout = 10_000
	val tree = fileTree(mapOf(Pair("dir", "build/reports/ktlint/**"), Pair("include", "*.txt")))
	codeQualityReports = mutableListOf(tree) as List<FileTree>?
}

tasks.withType<JacocoReport> {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacoco"))
	}
	afterEvaluate {
		classDirectories.setFrom(
            classDirectories.files.map {
                fileTree(it).matching {
                    exclude("**/config/**", "**/controller/**", "**/ContentApplication*.*")
                }
            }
        )
	}
}
tasks.withType<JacocoCoverageVerification> {
	dependsOn(tasks.jacocoTestReport)
	violationRules {
		isFailOnViolation = true
		rule {
			limit {
				minimum = "0.7".toBigDecimal()
			}
		}
	}
	afterEvaluate {
		classDirectories.setFrom(
            classDirectories.files.map {
                fileTree(it).matching {
                    exclude("**/config/**", "**/controller/**", "**/ContentApplication*.*")
                }
            }
        )
	}
}

tasks.getByName("check").dependsOn(
	"jacocoTestReport",
	"jacocoTestCoverageVerification"
)

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
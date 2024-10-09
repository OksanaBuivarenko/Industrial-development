plugins {
	jacoco
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.github.gradlecommunity.jaxb2") version "3.1.0"
}

group = "com.fintech"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
	imports {
		mavenBom("io.github.resilience4j:resilience4j-bom:2.1.0")
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://oss.sonatype.org/content/repositories/snapshots")
	}
}

jacoco {
	toolVersion = "0.8.12"
	reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required = false
		csv.required = true
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude("**/dto/**",
					"**/mapper/**",
					"**/adapter/**")
			}
		})
	)
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.mapstruct:mapstruct:1.6.2")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("org.glassfish.jaxb:jaxb-runtime")
	implementation("io.github.resilience4j:resilience4j-spring-boot3")
	implementation("io.github.resilience4j:resilience4j-all")
	implementation("io.github.resilience4j:resilience4j-reactor")
	implementation("io.micrometer:micrometer-registry-prometheus")

	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
	annotationProcessor( "org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor("org.projectlombok:lombok")

	compileOnly("org.projectlombok:lombok")
	compileOnly("com.google.guava:guava:28.1-jre")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.wiremock:wiremock-standalone:3.9.1")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
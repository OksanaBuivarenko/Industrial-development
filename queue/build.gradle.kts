plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("me.champeau.jmh") version "0.6.8"
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

repositories {
	mavenCentral()
	maven(url = "https://repo.e-iceblue.cn/repository/maven-public/")
	maven(url = "https://repo.e-iceblue.com/nexus/content/groups/public")


}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.openjdk.jmh:jmh-core:1.37")
	implementation("e-iceblue:spire.xls:14.10.0")
	implementation("e-iceblue:spire.doc:12.11.0")
	//implementation("com.aspose:aspose-cells:20.9")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

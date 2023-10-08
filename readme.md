# Langchain4j LEGO sample
This repository contains a sample project that demonstrates how to use the [langchain4j](https://github.com/langchain4j/langchain4j) library. In general I write these example projects to teach myself new technology, but I hope it is useful for others as well. Each project is used in one or more blogs. The blogs are listed in the section [Blogs that mention this project](#blogs-that-mention-this-project).

## Runnings the samples
This project makes use of Java 21 features. You can run the samples by executing the following command:
```shell
mvn clean compile exec:java -Dexec.mainClass="dev.jettro.RunEmbeddingsComparison"
```

## Blogs that mention this project
- [One embedding is not the other](https://jettro.dev/one-embedding-is-not-the-other-c4b806a1983c) Shows how to use multiple tools to create embeddings and how to use the langchain4j library to compare them. The start for the application is the java file [RunEmbeddingsComparison.java](src/main/java/dev/jettro/RunEmbeddingsComparison.java).

# Variables
GRADLE=./gradlew
MODULES=$(shell ls -d */ | grep db)  # Lists db-h2-connection, db-postgres-connection, etc.
DOCKER_COMPOSE_FILE=docker-compose.yml

# Commands
.PHONY: all clean build test run help

# Default: Build and test all modules
all: build test

# Clean the project
clean:
	$(GRADLE) clean
	@echo "Cleaned all build files."

# Runs the same thing as the pipeline.
.PHONY: pipeline
pipeline: build

# Build the entire project
build:
	$(GRADLE) \
			build \
			:db-h2-connection:build \
			:db-postgres-connection:build \
			:db-mysql-connection:build
	@echo "Built the entire project."

# Test the entire project
test:
	$(GRADLE) test
	@echo "Tested the entire project."


# Run the root Spring Boot application (if applicable)
run:
	$(GRADLE) bootRun
	@echo "Running the root Spring Boot application."

# Run individual modules
run-h2:
	$(GRADLE) :db-h2-connection:bootRun
	@echo "Running db-h2-connection module."

run-postgres:
	$(GRADLE) :db-postgres-connection:bootRun
	@echo "Running db-postgres-connection module."

run-mysql:
	$(GRADLE) :db-mysql-connection:bootRun
	@echo "Running db-mysql-connection module."

# Build and test a specific module
run-module:
	@read -p "Enter the module name (e.g., db-h2-connection): " MODULE && \
	$(GRADLE) :$$MODULE:clean :$$MODULE:build :$$MODULE:test && \
	echo "Built and tested $$MODULE."

# Help menu
help:
	@echo "Usage:"
	@echo "  make all         - Build and test all modules"
	@echo "  make clean       - Clean all build files"
	@echo "  make build       - Build all modules"
	@echo "  make test        - Test all modules"
	@echo "  make run         - Run the root application"
	@echo "  make run-module  - Build and test a specific module"
	@echo "  make help        - Show this help message"

OS := $(shell uname)

start:           ## Start the Docker containers
ifeq ($(OS),Darwin)
	docker-compose -f docker-compose.mac.override.yml up -d
else
	docker-compose up -d
endif

stop:           ## Stop the Docker containers
ifeq ($(OS),Darwin)
	docker-compose -f docker-compose.mac.override.yml stop
else
	docker-compose stop
endif

down:           ## Down the Docker containers
ifeq ($(OS),Darwin)
	docker-compose -f docker-compose.mac.override.yml down
else
	docker-compose down
endif
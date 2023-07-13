local-env-create:
	docker-compose -f stack.yaml up -d
	docker-compose -f stack.yaml build creditanalysisapp
	sleep 5
	docker cp data/ddl.sql credit-analysis:/var/lib/postgresql/data
	docker exec credit-analysis psql -h localhost -U admin -d postgres -a -f ./var/lib/postgresql/data/ddl.sql

local-env-destroy:
	docker-compose -f stack.yaml down
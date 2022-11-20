# DIO-Quebec-SpringBoot
Projeto de estacionamento para finalização de bootcamp DIO Québec

## Run database
docker run --name parking-db -p 5432:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres

## Stop database
docker stop parking-bd

## Start database
docker start parking-db
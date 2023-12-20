# Quorum API

## Introduction
This is a simple API for getting all the money expends of Sao Paulo's City Council members.

## Endpoints
All the endpoints can be found in the [swagger documentation](https://api.quorum-tech.io/swagger-ui/index.html?urls.primaryName=User%20API).
There's also a Postman collection you can download from [here](https://api.quorum-tech.io/postman/Quorum.postman_collection.json).

## Common use cases

<details>
<summary>Get all the expends for a specific council member</summary>

### Request
``` 
GET /v1/reembolsos?idVereador=1386 
```
### Response
```
[
	{
		"id": "0026a323-7bd1-4c37-95d2-49685a2304f5",
		"idVereador": "1386",
		"nomeVereador": "ISAC FELIX",
		"idCentroCusto": 386.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2020,
		"mes": 10,
		"nomeDespesa": "CONTRATAÇAO DE PESSOA JURIDICA",
		"idDespesa": "5d72c2e7-2dc7-4de5-993a-5bb69ea2f391",
		"cnpj": "67002329000162",
		"fornecedor": "WORK LINE SYSTEM INFORMÁTICA LTDA",
		"valor": 1040.0,
		"createdDate": "2023-12-20T21:52:50Z",
		"modifiedDate": "2023-12-20T21:52:50Z"
	},
	{
		"id": "005a4b48-9e22-4d20-85d6-be069daf140b",
		"idVereador": "1386",
		"nomeVereador": "ISAC FELIX",
		"idCentroCusto": 386.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2018,
		"mes": 1,
		"nomeDespesa": "COMPOSIÇÃO/ARTE/DIAGRAMAÇÃO/PRODUÇÃO/IMPRESSAO GRAFICA",
		"idDespesa": "b86d108f-7a15-41f3-b1d8-dea72a6fd2ec",
		"cnpj": "09520361000133",
		"fornecedor": "ECO WORLD PRODUTOS E SERVICOS CORPORATIVOS LTDA",
		"valor": 7980.0,
		"createdDate": "2023-12-20T22:03:12Z",
		"modifiedDate": "2023-12-20T22:03:12Z"
	},
	{
		"id": "014223ce-7488-4b62-b6bb-5691e74c2f4a",
		"idVereador": "1386",
		"nomeVereador": "ISAC FELIX",
		"idCentroCusto": 386.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2019,
		"mes": 9,
		"nomeDespesa": "MATERIAL DE ESCRITORIO E OUTROS MATERIAIS DE CONSUMO",
		"idDespesa": "380c745b-b7ec-4e41-925a-5fa49ed9f5e8",
		"cnpj": "43283811003256",
		"fornecedor": "KALUNGA S/A",
		"valor": 1575.44,
		"createdDate": "2023-12-20T21:53:43Z",
		"modifiedDate": "2023-12-20T21:53:43Z"
	}
]
```
</details>

<br/>
<details>
<summary> Get all the money expends for all councils on a specific year</summary>

### 
### Request
``` 
GET /v1/reembolsos?ano=2022 
```
### Response
```
[
    {
		"id": "00b424ef-716c-46c0-874a-846c677ac292",
		"idVereador": "1494",
		"nomeVereador": "SONAIRA FERNANDES",
		"idCentroCusto": 494.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2022,
		"mes": 1,
		"nomeDespesa": "ELABORAÇÃO/MANUTENÇAO DE SITE/HOSPEDAGEM",
		"idDespesa": "575018f7-f0d1-46c8-8f07-52b19fbe8f8e",
		"cnpj": "23584331000111",
		"fornecedor": "GENTE - AGENCIA DE PUBLICIDADE EIRELI - ME",
		"valor": 7300.0,
		"createdDate": "2023-12-20T21:46:49Z",
		"modifiedDate": "2023-12-20T21:46:49Z"
	},
	{
		"id": "01d11821-0461-45ec-a20a-c5d120553c0f",
		"idVereador": "1378",
		"nomeVereador": "ANDRÉ SANTOS",
		"idCentroCusto": 378.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2022,
		"mes": 1,
		"nomeDespesa": "CONTRATAÇAO DE PESSOA JURIDICA",
		"idDespesa": "5d72c2e7-2dc7-4de5-993a-5bb69ea2f391",
		"cnpj": "11521613000190",
		"fornecedor": "TECNEGOCIOS SOLUÇOES EM INFORMATICA LTDA- ME",
		"valor": 599.0,
		"createdDate": "2023-12-20T21:46:48Z",
		"modifiedDate": "2023-12-20T21:46:48Z"
	},
	{
		"id": "0316b83c-8b82-4de9-96cb-0c1b66495de1",
		"idVereador": "1388",
		"nomeVereador": "JOÃO JORGE",
		"idCentroCusto": 388.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2022,
		"mes": 1,
		"nomeDespesa": "INTERMEDIADO - LOCAÇÃO VEICULOS HIBRIDOS",
		"idDespesa": "575c1442-2347-41db-b8e0-8fcf811bf8fb",
		"cnpj": "50176288000128",
		"fornecedor": "CAMARA MUNICIPAL DE SÃO PAULO",
		"valor": 4800.0,
		"createdDate": "2023-12-20T21:46:48Z",
		"modifiedDate": "2023-12-20T21:46:48Z"
	}
]
```
</details>

<br/>

<details>
<summary> Get all the expends paid for some specific supplier</summary>

### Request
``` 
/v1/reembolsos?cnpj=07679089000103
```

### Sample response:
```
[
	{
		"id": "0001dab3-15eb-4d7c-b338-3bfc64fa7ef6",
		"idVereador": "1394",
		"nomeVereador": "RUTE COSTA",
		"idCentroCusto": 394.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2023,
		"mes": 2,
		"nomeDespesa": "COMBUSTIVEL",
		"idDespesa": "016c0dab-8167-4444-8e57-2491794298ac",
		"cnpj": "07679089000103",
		"fornecedor": "AUTO POSTO INDEPENDENCIA DO CAMBUCI LTDA.",
		"valor": 447.7,
		"createdDate": "2023-12-20T21:45:05Z",
		"modifiedDate": "2023-12-20T21:45:05Z"
	},
	{
		"id": "000b9534-a8ed-4453-be63-2f7fed60f1e8",
		"idVereador": "1394",
		"nomeVereador": "RUTE COSTA",
		"idCentroCusto": 394.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2019,
		"mes": 9,
		"nomeDespesa": "COMBUSTIVEL",
		"idDespesa": "016c0dab-8167-4444-8e57-2491794298ac",
		"cnpj": "07679089000103",
		"fornecedor": "AUTO POSTO INDEPENDENCIA DO CAMBUCI LTDA.",
		"valor": 695.15,
		"createdDate": "2023-12-20T21:53:44Z",
		"modifiedDate": "2023-12-20T21:53:44Z"
	},
	{
		"id": "0023e468-1484-4bb4-9934-84c7227d3ed4",
		"idVereador": "1394",
		"nomeVereador": "RUTE COSTA",
		"idCentroCusto": 394.0,
		"departamento": "GABINETE DE VEREADOR",
		"tipoDepartamento": 1,
		"ano": 2019,
		"mes": 8,
		"nomeDespesa": "COMBUSTIVEL",
		"idDespesa": "016c0dab-8167-4444-8e57-2491794298ac",
		"cnpj": "07679089000103",
		"fornecedor": "AUTO POSTO INDEPENDENCIA DO CAMBUCI LTDA.",
		"valor": 978.24,
		"createdDate": "2023-12-20T21:53:42Z",
		"modifiedDate": "2023-12-20T21:53:42Z"
	}
]
```

</details>

# Quorum API

## Introdução
Este projeto contém o código fonte de uma API para obter os gastos públicos dos membros da Câmara Municipal de São Paulo.
Os dados são fornecidos pelo [sistema SisGV](https://www.saopaulo.sp.leg.br/transparencia/dados-abertos/dados-disponibilizados-em-formato-aberto), disponíveis publicamente sob a Lei de Acesso à Informação do Brasil.

## Motivação
Os dados fornecidos pelo sistema da prefeitura de São Paulo não são muito amigáveis ou práticos:
- É uma API SOAP, que não é muito moderno
- Não é muito bem documentada
- Não é muito bem estruturada, já que os objetos nas respostas não são tipados adequadamente. Na maioria das vezes, os registros sequer possuem uma chave única
- Só é possível fazer solicitações filtrando por um ano e mês específicos, o que dificulta obter todos os dados de uma só vez ou aplicar filtros durante a requisição

Com isso em mente, este projeto obtém os dados do sistema SisGV, mapeia esses dados em estruturas adequadas, salva-os em um banco de dados e os expõe em endpoints REST mais fáceis de consumir.

## Endpoints
Todos os endpoints podem ser encontrados na página do [Swagger](https://api.quorum-tech.io/swagger-ui/index.html?urls.primaryName=User%20API).
Também há uma coleção do Postman que você pode baixar [aqui](https://www.postman.com/thiagobarbosa-br/workspace/quorum).

## Casos de uso comuns
Estes são alguns casos de uso comuns que você pode fazer com esta API:
<br/><br/>
<details>
<summary>Obter todos os gastos de um vereador</summary>

### Requisição
``` 
GET /v1/reembolsos?idVereador=1386 
```
### Resposta
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
<summary> Obter os gastos de todos os vereadores em um ano específico </summary>

### 
### Requisição
``` 
GET /v1/reembolsos?ano=2022 
```
### Resposta
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
<summary>Obter todos os gastos feitos para uma empresa específica</summary>

### Requisição
``` 
/v1/reembolsos?cnpj=07679089000103
```

### Resposta:
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

## Sobre autenticação
Para usar esta API, você precisa de um token de autenticação. Existem 2 tipos de tokens que você pode obter: um token público ou privado.

<details>
<summary>Token público</summary>

Este é um token que você pode gerar livremente sem qualquer informação pessoal. Ele é útil para fins de testes ou se você apenas quiser explorar a API.
Você pode gerá-lo através deste endpoint:
``` 
POST /v1/auth/publico/criar
```
Tokens públicos só podem fazer até 10 solicitações por minuto e expiram após 7 dias.

</details>

<br/>

<details>
<summary>Token privado</summary>

Este é um token que você pode gerar usando seu endereço de e-mail. É útil se você quiser usar a API em um ambiente de produção, 
ou se quiser usar a API com maior frequência.
Você pode gerá-lo através deste endpoint:

``` 
POST /v1/auth/privado/criar

Parameter:
email: <String>
(use form URL Encoded)
```

O token será enviado para o seu endereço de e-mail.
Tokens privados podem fazer até 100 solicitações por minuto e expiram após 1 ano.
Cada e-mail pode ter somente um token privado.
</details>

## Contribuições
Se você quiser contribuir para este projeto, sinta-se à vontade para abrir um pull request ou iniciar uma discussão via issues.

## Licença
Este projeto está sob a [licença MIT](https://github.com/thiagobarbosa/quorum-api/tree/main?tab=MIT-1-ov-file).
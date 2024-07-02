# Waste Sorting API Documentation

## DisposalGuideline API

### GET: Retrieve all disposal guidelines

```sh
curl -X GET http://localhost:8080/api/disposal-guidelines
```

### GET: Retrieve a disposal guideline by ID
```sh
curl -X GET http://localhost:8080/api/disposal-guidelines/{id}
```

### POST: Create a new disposal guideline
```sh
curl -X POST http://localhost:8080/api/disposal-guidelines \
-H "Content-Type: application/json" \
-d '{
  "guideline": "Dispose in blue bin"
}'
```

### PUT: Update an existing disposal guideline
```sh
curl -X PUT http://localhost:8080/api/disposal-guidelines/{id} \
-H "Content-Type: application/json" \
-d '{
  "guideline": "Updated guideline"
}'
```

### DELETE: Delete a disposal guideline
```sh
curl -X DELETE http://localhost:8080/api/disposal-guidelines/{id}
```
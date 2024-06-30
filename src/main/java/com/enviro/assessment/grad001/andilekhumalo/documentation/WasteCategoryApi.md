# Waste Sorting API Documentation

## WasteCategory API

### GET: Retrieve all waste categories

```sh
curl -X GET http://localhost:8080/api/waste-categories
```

### GET: Retrieve a waste category by ID
```sh
curl -X GET http://localhost:8080/api/waste-categories/{id}
```

### POST: Create a new waste category
```sh
curl -X POST http://localhost:8080/api/waste-categories \
-H "Content-Type: application/json" \
-d '{
  "category": "Plastic"
}'
```

### PUT: Update an existing waste category
```sh
curl -X PUT http://localhost:8080/api/waste-categories/{id} \
-H "Content-Type: application/json" \
-d '{
  "category": "Updated Plastic"
}'
```

### DELETE: Delete a waste category
```sh
curl -X DELETE http://localhost:8080/api/waste-categories/{id}
```
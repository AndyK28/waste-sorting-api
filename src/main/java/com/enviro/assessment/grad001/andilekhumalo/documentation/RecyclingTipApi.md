# Waste Sorting API Documentation

## RecyclingTip API

### GET: Retrieve all recycling tips

```sh
curl -X GET http://localhost:8080/api/recycling-tips
```

### GET: Retrieve a recycling tip by ID
```sh
curl -X GET http://localhost:8080/api/recycling-tips/{id}
```

### POST: Create a new recycling tip
```sh
curl -X POST http://localhost:8080/api/recycling-tips \
-H "Content-Type: application/json" \
-d '{
  "tip": "Crush cans to save space"
}'
```

### PUT: Update an existing recycling tip
```sh
curl -X PUT http://localhost:8080/api/recycling-tips/{id} \
-H "Content-Type: application/json" \
-d '{
  "tip": "Updated recycling tip"
}'
```

### DELETE: Delete a recycling tip
```sh
curl -X DELETE http://localhost:8080/api/recycling-tips/{id}
```
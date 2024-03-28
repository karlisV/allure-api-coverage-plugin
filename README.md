This plugin adds a "Coverage" tab to the Allure reports
Used to map together APIs called during integration testing with Swagger doc and calculate the endpoint coverage.
This is only for data representation, the plugin does not do any calculations.
UI is wip currently.
Usage:
1) Add the plugin properly to the allure report generator
2) After the tests are done a file called "coverage.json" should be generated in the result directory
3) The file should be in this format (all keys are mandatory):
```json
{
  "coverage": 99.99,
  "documented_endpoints": [
    {
      "url": "GET /foo",
      "hit_count": 1
    },
    {
      "url": "POST /foo",
      "hit_count": 1
    },
    {
      "url": "PUT /bar",
      "hit_count": 0
    },
    {
      "url": "GET /buzz",
      "hit_count": 0
    }
  ],

  "not_documented_endpoints": [
    {
      "url": "GET /_/foo",
      "hit_count": 1
    },
    {
      "url": "POST /_/foo",
      "hit_count": 1
    },
    {
      "url": "PUT /_/bar",
      "hit_count": 1
    }
  ]
}
```
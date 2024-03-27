This plugin adds a "Coverage" tab to the Allure reports
This is only for data representation, the plugin does not do any calculations.

Usage:
1) Add the plugin properly to the allure report generator
2) After the tests are done a file called "coverage.json" should be generated in the result directory
3) The file should be in this format (all keys are mandatory):
```json
{
  "endpoints_covered": [
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
      "hit_count": 1
    }
  ],

  "endpoints_not_covered":[
    "GET /baz",
    "POST /bar"
  ],

  "endpoints_not_documented": [
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
  ],
  "coverage": 99.99
}

```
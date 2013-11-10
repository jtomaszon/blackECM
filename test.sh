#!/bin/bash

server="http://localhost:8080"
workflows="${server}/workflows"

curl_() {
  curl -i -H "Accept: application/json" $@
}

post() {
  curl_ -X POST $@
}

get() {
  curl_ $@
}

put() {
  curl_ -X PUT $@
}

delete() {
  curl_ -X DELETE $@
}

get "${workflows}"
post -d "id=1&name=aee" "${workflows}"



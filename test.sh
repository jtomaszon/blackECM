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

echo "Create"
post -d "name=aee" "${workflows}"
get "${workflows}"
echo "========================"

echo "Update"
put -d "name=mavai" "${workflows}/1"
get "${workflows}"
echo "========================"

echo "Delete"
delete "${workflows}/1"
get "${workflows}"
echo "========================"

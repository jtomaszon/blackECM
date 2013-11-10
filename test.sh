#!/bin/bash

server="http://localhost:8080"
workflows="${server}/workflows"
requests="${server}/requests"


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

echo "Create"
post -d "user_id=2&workflow_id=1" "${requests}"
get "${requests}"
echo "========================"

echo "Update"
put -d "user_id=2&workflow_id=3" "${requests}/1"
get "${requests}"
echo "========================"

echo "Delete"
delete "${requests}/1"
get "${requests}"
echo "========================"

#!/bin/bash

server="http://localhost:8080"
workflows="${server}/workflows"
requests="${server}/requests"

rdm_name() {
  cat /dev/urandom | env LC_CTYPE=C tr -cd 'a-f0-9' | head -c 32
}

curl_() {
  echo -e "\n\nCURL $@..."
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


for i in $(seq 20); do
  post -d "name=$(rdm_name)" $workflows
  post -d "user_id=2&workflow_id=${i}" $requests
  j=$(( $i % 3 ))
  if [ $j -eq 0 ]; then
    delete "${workflows}/${i}"
    delete "${requests}/${i}"
  fi
  k=$(( $i % 2 ))
  if [ $k -eq 0 ]; then
    put -d "name=$(rdm_name)" "${workflows}/${i}"
    put -d "workflow_id=${i}" "${requests}/${i}"
  fi
done

get $workflows
get $requests

# echo "Create"
# post -d "name=aee" "${workflows}"
# get "${workflows}"
# echo "========================"

# echo "Update"
# put -d "name=mavai" "${workflows}/1"
# get "${workflows}"
# echo "========================"

# echo "Delete"
# delete "${workflows}/1"
# get "${workflows}"
# echo "========================"

# echo "Create"
# post -d "user_id=2&workflow_id=1" "${requests}"
# get "${requests}"
# echo "========================"

# echo "Update"
# put -d "user_id=2&workflow_id=3" "${requests}/1"
# get "${requests}"
# echo "========================"

# echo "Delete"
# delete "${requests}/1"
# get "${requests}"
# echo "========================"

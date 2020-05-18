# this is a hack to create all files that were changed by google
# otherwise the git apply 3 way merge does not work.

./mergeFromGoogle.sh /Users/lhilbert/Documents/dev/github/protobuf &> /tmp/leoapplyresult
result=$(cat /tmp/leoapplyresult | grep "does not exist in index" | sed -n -e 's/^error: \(.*\):.*/\1/p')
for i in $result; do
    #echo $(dirname $i)
    touch $i
done
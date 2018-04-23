#!/usr/bin/env python
"""counter.py"""

### This counter script is used to calculate the total number of unique tweets in the
### dataset (does not include retweets). The number of unique tweets is needed to
### normalize the results from the word count.

import sys
import json

sys.stdout = open("count_tweets.txt", "w")
count = 0
# input comes from STDIN (standard input)
for line in sys.stdin:
    # check if this next line contains a document and is not an empty line
    if line.startswith("{"):
        # use json.loads to convert tweet from json format to python
        json_data = json.loads(line)
        # check if retweeted_status is a key in json_data, if not it is not a retweet
        if 'retweeted_status' not in json_data:
            # if this is a original tweet, add to count
            count += 1
print (count)
sys.stdout.close()

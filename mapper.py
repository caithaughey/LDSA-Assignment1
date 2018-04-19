#!/usr/bin/env python
"""mapper.py"""

import sys
import json

# input comes from STDIN (standard input)
for line in sys.stdin:
    # use json module to convert tweet from json format to python
    json_data = json.loads(line)
    # check if retweeted_status is a key in json_data, if not it is not a retweet
    if 'retweeted_status' not in json_data:
       # select part of the json_data that contains a string of text by using the text key
        json_tweets = json_data.get('text')
        print(json_tweets)
        # remove leading and trailing whitespace
        json_tweets = json_tweets.strip()
        print(json_tweets)
        # split the line into words
        tweet_words = json_tweets.split()
        print(tweet_words)
        # lowercase all words in list
        tweet_words = [x.lower() for x in tweet_words]
        print(tweet_words)
        # define a list of keywords to search for
        keywords = ['han', 'hon', 'den', 'det', 'denna', 'denne', 'hen']
        # increase counters
        for pronoun in keywords:
            # check if pronoun is in the tweet
            if pronoun in tweet_words:
                # write the results to STDOUT (standard output);
                # what we output here will be the input for the
                # Reduce step, i.e. the input for reducer.py
                #
                # tab-delimited; the trivial word count is 1
                print '%s\t%s' % (pronoun, 1)

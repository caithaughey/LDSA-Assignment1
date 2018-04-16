//package org.myorg;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCountNew {
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable VALUE = new IntWritable(1);
        private Text letter = new Text();
     
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
              String token = tokenizer.nextToken();
              if(token.startsWith("a-z")) {
                String s = token.charAt(0);
                letter.set(s);
                output.collect(letter, VALUE);
            }
        }
    }
    }
 
    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
          for (IntWritable val: values) {
                sum += val.get();
            }
            output.collect(key, new IntWritable(sum));
        }
    }
 
    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(WordCountNew.class);
        conf.setJobName("lettercount");
     
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
     
        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);
     
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
     
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
     
        JobClient.runJob(conf);
    }
}
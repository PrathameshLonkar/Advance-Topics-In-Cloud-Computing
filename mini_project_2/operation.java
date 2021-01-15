import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      String cals[] = value.toString().split(" ");
        String op = cals[0].trim();
        word.set(op);

        IntWritable numbs = new IntWritable(Integer.parseInt(cals[1].trim()));
        context.write(word,numbs);
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int res = 1;
String op = key.toString();
        switch(op){
        case "and":
        {
        List<Integer> list = new ArrayList();
        for (IntWritable val : values) {
        list.add(val.get());


      }

        int a= list.get(0);
        for(int i=1;i<list.size();i++){
        a &= list.get(i);
        }
        result = new IntWritable(a);
        break;
        }
        case "or":
        {
        //List<Integer> list = new ArrayList();
        int a = 0;
        for (IntWritable val : values) {
        //list.add(val.get());
        a |= val.get();

      }
        result = new IntWritable(a);
        break;
        }
        case "xor":
        {
        List<Integer> list = new ArrayList();
        for (IntWritable val : values) {
        list.add(val.get());


      }

        int a= list.get(0);
        for(int i=1;i<list.size();i++){
        a ^= list.get(i);
        }
        result = new IntWritable(a);
        break;
        }
        case "add":
        {
        int a =0;
        for (IntWritable val : values) {
        a += val.get();


      }
        result = new IntWritable(a);
        break;
        }
        case "sub":
        {
        List<Integer> list = new ArrayList();
        for (IntWritable val : values) {
        list.add(val.get());


      }

        int a= list.get(0);
        for(int i=1;i<list.size();i++){
        a -= list.get(i);
        }
        result = new IntWritable(a);
        break;
        }

        case "mul":
        {
        int a=1;
        for (IntWritable val : values) {
        a *= val.get();


      }
 result = new IntWritable(a);
        break;
        }

        }


      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}


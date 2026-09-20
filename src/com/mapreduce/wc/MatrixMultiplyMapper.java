package com.mapreduce.wc;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MatrixMultiplyMapper
        extends Mapper<LongWritable, Text, Text, Text> {

    private int m;
    private int n;
    private int p;

    @Override
    protected void setup(Context context) {

        m = context.getConfiguration().getInt("m", 2);
        n = context.getConfiguration().getInt("n", 2);
        p = context.getConfiguration().getInt("p", 2);
    }

    @Override
    public void map(
            LongWritable key,
            Text value,
            Context context)
            throws IOException, InterruptedException {

        String[] parts = value.toString().split(",");

        String matrix = parts[0].trim();

        int row = Integer.parseInt(parts[1].trim());
        int col = Integer.parseInt(parts[2].trim());
        int val = Integer.parseInt(parts[3].trim());

        /*
         * Matrix A:
         *
         * A[i][k] contributes to
         * C[i][j] for every j.
         */

        if (matrix.equals("A")) {

            for (int j = 0; j < p; j++) {

                String outputKey =
                        row + "," + j;

                String outputValue =
                        "A," + col + "," + val;

                context.write(
                        new Text(outputKey),
                        new Text(outputValue)
                );
            }
        }

        /*
         * Matrix B:
         *
         * B[k][j] contributes to
         * C[i][j] for every i.
         */

        else if (matrix.equals("B")) {

            for (int i = 0; i < m; i++) {

                String outputKey =
                        i + "," + col;

                String outputValue =
                        "B," + row + "," + val;

                context.write(
                        new Text(outputKey),
                        new Text(outputValue)
                );
            }
        }
    }
}
package com.mapreduce.wc;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MatrixMultiplyReducer
        extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(
            Text key,
            Iterable<Text> values,
            Context context)
            throws IOException, InterruptedException {

        int[] A = new int[2];
        int[] B = new int[2];

        for (Text value : values) {

            String[] parts =
                    value.toString().split(",");

            String matrix =
                    parts[0];

            int index =
                    Integer.parseInt(parts[1]);

            int val =
                    Integer.parseInt(parts[2]);

            if (matrix.equals("A")) {

                A[index] = val;

            } else if (matrix.equals("B")) {

                B[index] = val;
            }
        }

        int result = 0;

        for (int k = 0; k < 2; k++) {

            result += A[k] * B[k];
        }

        context.write(
                key,
                new Text(String.valueOf(result))
        );
    }
}
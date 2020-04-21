package com.rojanprod.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rojanprod.trivia.controller.AppController;
import com.rojanprod.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();

    //This is the url from where we get the question answer array as json array
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    //This method will help to get question from the JsonRequest and returns a list of questions to the caller
    public List<Question> getQuestions(){


        //Fetching all the data from the url json array
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Traversing through the array of Q/A from 0 index to length of the response (array in json server)
                        for(int i=0 ; i<response.length() ; i++) {
                            try {

                                Log.d("JsonQuestion", "onResponse: " + response.getJSONArray(i).get(0));
                                Log.d("JsonAnswer", "onResponse: " + response.getJSONArray(i).getBoolean(1));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return null;
    }

}

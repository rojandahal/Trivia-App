package com.rojanprod.trivia.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rojanprod.trivia.controller.AppController;
import com.rojanprod.trivia.model.Question;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private ArrayList<Question> questionArrayList = new ArrayList<>();

    // This is the url from where we get the question answer array as json array
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    /** This method will get question from the JsonRequest and returns a list of questions to the caller
     * The AsyncQuestionList parameter is an interface which is used to indicate the ending of process and passing
     * the array of the list of question to the caller
     * */
    public List<Question> getQuestions(final AsyncQuestionList callback){


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

                                Question question = new Question();
                                question.setQuestionName(response.getJSONArray(i).get(0).toString());
                                question.setAnswer(response.getJSONArray(i).getBoolean(1));

                                //Log.d("JsonQuestion", "onResponse: " + response.getJSONArray(i).get(0));
                                //Log.d("JsonAnswer", "onResponse: " + response.getJSONArray(i).getBoolean(1));

                                questionArrayList.add(question);
                                //Log.d("QuestionArray", "onResponse: " + questionArrayList);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        /* If the Array of Q/A fetched is not empty then it will pass the arrayList to the method of callback Interface */
                        if(questionArrayList!=null) {
                            callback.processFinished(questionArrayList);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }

}

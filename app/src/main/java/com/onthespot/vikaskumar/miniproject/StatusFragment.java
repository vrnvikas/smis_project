package com.onthespot.vikaskumar.miniproject;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Context context;
    private static List<String> listDataStatus;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private EditText statusEditBox;
    private Button postButton;
    private UserStatusDataBase userStatusDataBase;
    private ArrayList<String> listData;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param baseContext
     * @param listData
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2, Context baseContext, List<String> listData) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        context = baseContext;
        listDataStatus = listData;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_status, container, false);
        setRecyclerView(layout);
        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeStausRequest(getActivity());
        statusEditBox = (EditText) view.findViewById(R.id.status_edit_text_box);
        postButton = (Button) view.findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForEmptyField()){
                    String status = statusEditBox.getText().toString();
                    if (Utility.isNetworkAvailable(context)) {
                        RequestUtility.statusPostRequest(constructHeader(),constructJsonObject(status),context);
                    }else {
                        Toast.makeText(context, "InterNet Not Working", Toast.LENGTH_LONG).show();
                    }

                }else {
                    statusEditBox.setError("Field is Empty");
                }
            }
        });


    }

    private StatusPostPojo constructJsonObject(String status) {
        StatusPostPojo statusPostPojo = new StatusPostPojo();
        statusPostPojo.setStatus(status);
        return statusPostPojo;
    }

    public boolean checkForEmptyField() {
        return !statusEditBox.getText().toString().isEmpty();
    }


    public void setRecyclerView(View layout){
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view_status);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        listData = new ArrayList<>();
        adapter = new RecyclerAdapter(context,listData);
        recyclerView.setAdapter(adapter);
    }


    private void makeStausRequest(final Context context) {

        RetroInterface i = Utility.createRetrofit();
        Call<List<StatusBodyPojo>> statusBodyPojoCall = i.getStatus(constructHeader());
        statusBodyPojoCall.enqueue(new Callback<List<StatusBodyPojo>>() {
            @Override
            public void onResponse(Call<List<StatusBodyPojo>> call, Response<List<StatusBodyPojo>> response) {
                //Log.i("vikas",response.body().get(0).getPostContent()+ "-post Content" );

                if(response.body() != null){
                    //Utility.parseDatabase(response,context);
                    List<String> list = new ArrayList<String>();
                    for(StatusBodyPojo body:response.body()){
                        Log.i("vikas","data first----"+ body.getPostContent());
                        list.add(body.getPostContent());
                    }
                    adapter.swap(list);
                }

            }

            @Override
            public void onFailure(Call<List<StatusBodyPojo>> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String constructHeader() {
        String hashPassword = Utility.getHashString("abcd", "SHA-1");
        String header = "admin3" + ":" + "abcd";
        return "Basic " + Base64.encodeToString(header.getBytes(), Base64.NO_WRAP);
    }


    public List<UserStausModel>  getData(){
        List<UserStausModel> data;
        userStatusDataBase = UserStatusDataBase.newInstance(context);
        data = userStatusDataBase.viewAllData();
        return  data;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

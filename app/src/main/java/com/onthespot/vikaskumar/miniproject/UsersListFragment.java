package com.onthespot.vikaskumar.miniproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsersListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecyclerAdapterUserList adapter;
    private List<User> listUser;
    List<Integer> listFollowing;


    public UsersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersListFragment newInstance(String param1, String param2,Context baseContext) {
        UsersListFragment fragment = new UsersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        context = baseContext;
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
        View layout = inflater.inflate(R.layout.fragment_user_list, container, false);
        setRecyclerView(layout);
        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeUserListRequest(getActivity());
    }

    private void makeUserListRequest(final Context context) {

        RetroInterface i = Utility.createRetrofit();
        Call<UserListBodyPojo> userListBodyPojoCall = i.getUserList(constructHeader());
        userListBodyPojoCall.enqueue(new Callback<UserListBodyPojo>() {
            @Override
            public void onResponse(Call<UserListBodyPojo> call, Response<UserListBodyPojo> response) {

                if(response.body() != null){

                     listFollowing = new ArrayList<>();
                    listUser = new ArrayList<>();

                        listFollowing = response.body().getFollowing();
                        listUser = response.body().getUsers();


                    adapter.swap(listFollowing,listUser);
                }else {
                    Toast.makeText(context, "" + "Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserListBodyPojo> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String constructHeader() {
        String userEmail = Utility.getUserEmail(context);
        String userToken = Utility.getUserToken(context);
        String hashPassword = Utility.getHashString(userToken, "SHA-1");
        String header = userToken + ":" + "None";
        return "Basic " + Base64.encodeToString(header.getBytes(), Base64.NO_WRAP);
    }

    private void setRecyclerView(View layout) {
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new RecyclerAdapterUserList(context,listFollowing,listUser);
        recyclerView.setAdapter(adapter);
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

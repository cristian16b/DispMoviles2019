package com.holamundo.ciudaduniversitariainteligente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BedeliaMovil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BedeliaMovil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BedeliaMovil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONObject listaHorariosFacultades;
    private Spinner spinnerFacultad = null;


    public BedeliaMovil() {
        // Required empty public constructor
        //this.spinnerFacultad = (Spinner) spinnerFacultad.findViewById(0);
        this.listaHorariosFacultades = new JSONObject();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BedeliaMovil.
     */
    // TODO: Rename and change types and number of parameters
    public static BedeliaMovil newInstance(String param1, String param2) {
        BedeliaMovil fragment = new BedeliaMovil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

        View view = inflater.inflate(R.layout.fragment_bedelia_movil, container, false);

        spinnerFacultad = (Spinner) view.findViewById(R.id.facultadSpinner);

        //cargo los valores de spinner
        final String[] facultades = {" --- ", "FICH", "FCBC", "FADU", "FHUC"};
        ArrayAdapter <String>adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,facultades);
        spinnerFacultad.setAdapter(adapter);

        spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() // register the listener
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // User selected item
                //Toast.makeText(getActivity().getApplicationContext(), facultades[position] + " selected!", Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //llamo al webservice
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //webservice publico fake , ingresar a la url para ver la estructura json de los datos
        String url = "https://my-json-server.typicode.com/cristian16b/DispMoviles2019/db";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jso = new JSONObject(response);

                    //accedo al subarray bedelia
                    JSONArray jregular = jso.getJSONArray("bedelia");
                    //accedo al primer elemento (listado de facultades)
                    listaHorariosFacultades = jregular.getJSONObject(0);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        // Inflate the layout for this fragment
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */

    /*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

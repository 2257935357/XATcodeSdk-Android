package com.xa.trcode.transcodeexamplesdkdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xa.transcode.XATSCodeSDK;
import com.xa.transcode.bean.XACatalog;
import com.xa.transcode.maintranscode.IBooksCatalogueCallback;
import com.xa.trcode.transcodeexamplesdkdemo.R;
import com.xa.trcode.transcodeexamplesdkdemo.SecondActivity;
import com.xa.trcode.transcodeexamplesdkdemo.adapter.CatalogAdapter;

import java.util.ArrayList;
import java.util.List;


public class CatalogListFragment extends Fragment {


    private static final String ARG_PARAM1_URL = "ARG_PARAM1_URL";

    // TODO: Rename and change types of parameters
    private String currentBookUrl;
    private String mParam2;
    private RecyclerView catalogRecyclerView;
    private CatalogAdapter catalogAdapter = new CatalogAdapter(new ArrayList<XACatalog>());

    public static CatalogListFragment newInstance(String url) {
        CatalogListFragment fragment = new CatalogListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            currentBookUrl = getArguments().getString(ARG_PARAM1_URL);
        }
        catalogRecyclerView = view.findViewById(R.id.catalog_recycler_view);
        catalogRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        catalogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        catalogRecyclerView.setAdapter(catalogAdapter);


        catalogAdapter.setItemOnClick(new CatalogAdapter.ItemOnClick() {
            @Override
            public void onClick(String name, String url, int index) {
                if (getActivity() instanceof SecondActivity) {
                    SecondActivity secondActivity = (SecondActivity) getActivity();
                    secondActivity.setCurrentBookName(name, url, index);
                }
            }
        });

        XATSCodeSDK.getInstanceSdk().obtainBooksCatalogue(currentBookUrl, new IBooksCatalogueCallback() {
            @Override
            public void onSuccess(List<XACatalog> result) {
                catalogAdapter.setNewData(result);
                if (getActivity() instanceof SecondActivity) {
                    SecondActivity secondActivity = (SecondActivity) getActivity();
                    secondActivity.getCatalogList(result);
                    secondActivity.setCurrentBookName(result.get(0).getTitle(), result.get(0).getUrl(), 0);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    Toast.makeText(getContext(), "获取目录失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
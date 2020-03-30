package com.example.cwork.productview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cwork.CallbackFailureFragment;
import com.example.cwork.LoginFragment;
import com.example.cwork.MainActivity;
import com.example.cwork.NavigationHost;
import com.example.cwork.R;
import com.example.cwork.SportNewsFragment;
import com.example.cwork.network.ProductEntry;
import com.example.cwork.retrofitProduct.ProductDTO;
import com.example.cwork.retrofitProduct.ProductDTOService;
import com.example.cwork.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGridFragment extends Fragment {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

//        List<ProductEntry> list = ProductEntry.initProductEntryList(getResources());
//        ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(list);
//
//        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        CommonUtils.showLoading(getActivity());

        ProductDTOService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                        Log.d(TAG, "-------------Response OK-------------");
//                        switch (response.code()){
//                            case 200:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                List<ProductDTO> list = response.body();
//                                List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
//                                for (ProductDTO item : list) {
//                                    ProductEntry pe = new ProductEntry(item.getTitle(),item.getUrl(),item.getUrl(), item.getPrice(),"sdfasd");
//                                    newlist.add(pe);
//                                }
//                                ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);
//                                recyclerView.swapAdapter(newAdapter, false);
//                                break;
//                            case 403:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Доступ заборонено"), false);
//                                break;
//                            case 404:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Ресурс не знайдено"), false);
//                                break;
//                            case 500:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Внутрішня помилка серверу"), false);
//                                break;
//                            default:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                break;
//                        }

                        if (!((NavigationHost)getActivity()).isConnect(response)){
                            CommonUtils.hideLoading();
                            return;
                        }

                        List<ProductDTO> list = response.body();
                        List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
                        for (ProductDTO item : list) {
                            ProductEntry pe = new ProductEntry(item.getTitle(),item.getUrl(),item.getUrl(), item.getPrice(),"sdfasd");
                            newlist.add(pe);
                        }
                        ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);
                        recyclerView.swapAdapter(newAdapter, false);

//                        uploadData();
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                        Log.e(TAG, "-------------Bad request-------------");
                        ((NavigationHost)getActivity())
                                .navigateTo(new CallbackFailureFragment(
                                        "З'єднання розірвано"), false);

                        CommonUtils.hideLoading();
                    }
                });

        return view;
    }

    public boolean isConnect(Response response){

        switch (response.code()){
            case 200:
                Log.d(TAG, Integer.toString(response.code()));
//                List<ProductDTO> list = response.body();
//                List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
//                for (ProductDTO item : list) {
//                    ProductEntry pe = new ProductEntry(item.getTitle(),item.getUrl(),item.getUrl(), item.getPrice(),"sdfasd");
//                    newlist.add(pe);
//                }
//                ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);
//                recyclerView.swapAdapter(newAdapter, false);
                return true;
            case 403:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Доступ заборонено"), false);
                break;
            case 404:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Ресурс не знайдено"), false);
                break;
            case 500:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Внутрішня помилка серверу"), false);
                break;
            default:
                Log.d(TAG, Integer.toString(response.code()));
                break;
        }

        return false;
    }
}

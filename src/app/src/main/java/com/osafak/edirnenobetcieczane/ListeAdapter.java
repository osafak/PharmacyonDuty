package com.osafak.edirnenobetcieczane;

/**
 * Created by Onur on 5.01.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mobiltrakya on 03/01/17.
 */

public class ListeAdapter extends RecyclerView.Adapter<ListHolder> {

    private List<PharmaModel> mPharmaModels;
    public Context mContext;

    public ListeAdapter(List<PharmaModel> Pharmacies, Context context) {
        mPharmaModels = Pharmacies;
        mContext = context;

    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater = android.view.LayoutInflater.from(mContext);
        View v = LayoutInflater.inflate(R.layout.list_item, parent, false);

        return new ListHolder(v);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        PharmaModel mPharma = mPharmaModels.get(position);
        holder.bindHolder(mPharma);


    }

    @Override
    public int getItemCount() {
        return mPharmaModels.size();
    }
}

class ListHolder extends RecyclerView.ViewHolder {
    private TextView mPharmacyName;
    private TextView mPhone;
    private TextView mAdress;
    private Button mCall;

    public ListHolder(View itemView) {
        super(itemView);
        mPharmacyName = (TextView) itemView.findViewById(R.id.pharmacyName);
        mAdress = (TextView) itemView.findViewById(R.id.adress);
        mPhone = (TextView) itemView.findViewById(R.id.phone_number);
        mCall = (Button) itemView.findViewById(R.id.call_button);
    }

    public void bindHolder(PharmaModel Pharma) {
        mAdress.setText(Pharma.getAddress());
        mPhone.setText(Pharma.getPhone());
        mPharmacyName.setText(Pharma.getName());
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mPhone.getText().toString().trim()));
                view.getContext().startActivity(callIntent);

            }
        });
    }

}

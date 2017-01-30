package mjkarbasian.moshtarimadar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Setting.MySetting;

/**
 * Created by family on 12/30/2016.
 */
public class KasebDashBoard extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region handle sharepreference
        String kasebPREFERENCES = "kasebProfile";
        SharedPreferences kasebSharedPreferences = getContext().getSharedPreferences(kasebPREFERENCES, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = kasebSharedPreferences.edit();

        if (kasebSharedPreferences.getString("firstName", null) == null) {
            Intent intent = new Intent(getActivity(), MySetting.class);
            startActivity(intent);
        }
        //endregion handle sharepreference
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //region define charts variables
        Float totalCusNum = 0f;
        Float totalGoldCusNum = 0f;
        Float totalSilvCusNum = 0f;
        Float totalBronzCusNum = 0f;
        Float totalInStartCusNum = 0f;
        Float totalSales = 0f;
        Float totalSalesGold = 0f;
        Float totalSalesSilv = 0f;
        Float totalSalesBronz = 0f;
        Float totalSalesNew = 0f;
        Float totalRec = 0f;
        Float totalRecGold = 0f;
        Float totalRecSilv = 0f;
        Float totalRecBronz = 0f;
        Float totalRecNew = 0f;
        //endregion

        View rootView = inflater.inflate(R.layout.fragment_kaseb_dashboard, container, false);

        //region set customers dashboard
        Cursor cursor = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            totalCusNum = (float) cursor.getCount();
        }
        cursor = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null,
                KasebContract.Customers.COLUMN_STATE_ID + " = ?", new String[]{"1"}, null);
        if (cursor != null) {
            totalGoldCusNum = (float) cursor.getCount();

        }

        cursor = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null,
                KasebContract.Customers.COLUMN_STATE_ID + " = ?", new String[]{"2"}, null);
        if (cursor != null) {
            totalSilvCusNum = (float) cursor.getCount();

        }
        cursor = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null,
                KasebContract.Customers.COLUMN_STATE_ID + " = ?", new String[]{"3"}, null);
        if (cursor != null) {
            totalBronzCusNum = (float) cursor.getCount();

        }
        cursor = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null,
                KasebContract.Customers.COLUMN_STATE_ID + " = ?", new String[]{"4"}, null);
        if (cursor != null) {
            totalInStartCusNum = (float) cursor.getCount();
        }

        //endregion

        //region set Total Sales and recievables
        String[] projection = new String[]{"sum(" + KasebContract.DetailSale.TABLE_NAME + "." + KasebContract.DetailSale.COLUMN_TOTAL_DUE + ") as total"};
        String[] recievProj = new String[]{"sum(" + KasebContract.DetailSale.TABLE_NAME + "." + KasebContract.DetailSale.COLUMN_TOTAL_PAID + ") as total"};
        String selection = KasebContract.Sales.TABLE_NAME + "." + KasebContract.Sales.COLUMN_IS_DELETED + " =? ";
        String[] selectArg = new String[]{"0"};

        Cursor recievCurs = null;
        cursor = getContext().getContentResolver().query(KasebContract.Sales.saleDetailSaleJoin(), projection, selection, selectArg, null);
        recievCurs = getContext().getContentResolver().query(KasebContract.Sales.saleDetailSaleJoin(), recievProj, selection, selectArg, null);

        assert cursor != null;
        if (cursor.moveToFirst())
            totalSales = cursor.getString(0) == null ? 0f : cursor.getLong(0);
        assert recievCurs != null;
        if (recievCurs.moveToFirst())
            totalRec = cursor.getString(0) == null ? 0f : cursor.getLong(0) - recievCurs.getLong(0);


        //endregion

        //region defining views
        PieChart statePie = (PieChart) rootView.findViewById(R.id.pie_chart_customer_state);
        PieChart salePie = (PieChart) rootView.findViewById(R.id.pie_chart_sale);
        PieChart recPie = (PieChart) rootView.findViewById(R.id.pie_chart_recievable);
        //endregion defining views

        //region defining variables
        selection = KasebContract.Sales.TABLE_NAME + "." + KasebContract.Sales.COLUMN_IS_DELETED + " =? " + " AND " +
                KasebContract.Sales.COLUMN_CUSTOMER_ID + " =?";
        //iterate for 4 main customer state. i represent state id of gold,silver,bronze and in start
        Long[] totalDue = new Long[5];
        Long[] totalPaid = new Long[5];
        Cursor customerCurs = null;
        //endregion defining variables

        //region set memberships due and recievables
        for (int i = 0; i < 5; i++) {
            totalDue[i] = 0l;
            totalPaid[i] = 0l;
            customerCurs = getContext().getContentResolver().query(KasebContract.Customers.CONTENT_URI, null,
                    KasebContract.Customers.COLUMN_STATE_ID + " = ?", new String[]{String.valueOf(i)}, null);
            assert customerCurs != null;
            while (customerCurs.moveToNext()) {
                selectArg = new String[]{"0", customerCurs.getString(customerCurs.getColumnIndex(KasebContract.Customers._ID))};
                cursor = getContext().getContentResolver().query(KasebContract.Sales.saleDetailSaleJoin(), projection, selection, selectArg, null);
                recievCurs = getContext().getContentResolver().query(KasebContract.Sales.saleDetailSaleJoin(), recievProj, selection, selectArg, null);
                if (cursor.moveToFirst())
                    totalDue[i] += cursor.getLong(0);
                if (recievCurs.moveToFirst())
                    totalPaid[i] += recievCurs.getLong(0);
            }
        }

        //region setViews of membership
        totalSalesGold = totalDue[1] == null ? 0f : totalDue[1];
        totalSalesSilv = totalDue[2] == null ? 0f : totalDue[2];
        totalSalesBronz = totalDue[3] == null ? 0f : totalDue[3];
        totalSalesNew = totalDue[4] == null ? 0f : totalDue[4];
        totalRecGold = totalDue[1] == null ? 0f : (totalDue[1] - totalPaid[1]);
        totalRecSilv = totalDue[2] == null ? 0f : (totalDue[2] - totalPaid[2]);
        totalRecBronz = totalDue[3] == null ? 0f : (totalDue[3] - totalPaid[3]);
        totalRecNew = totalDue[4] == null ? 0f : (totalDue[4] - totalPaid[4]);
        //endregion

        //region define customer state pie chart
        List<PieEntry> stateEntries = new ArrayList<>();
        List<Integer> dataColors = new ArrayList<>();

        if (totalGoldCusNum != 0) {
            stateEntries.add(new PieEntry(totalGoldCusNum, getActivity().getResources().getString(R.string.states_gold)));
            dataColors.add(ContextCompat.getColor(getContext(), R.color.gold));
        }

        if (totalSilvCusNum != 0) {
            stateEntries.add(new PieEntry(totalSilvCusNum, getActivity().getResources().getString(R.string.states_silver)));
            dataColors.add(ContextCompat.getColor(getContext(), R.color.silver));
        }

        if (totalBronzCusNum != 0) {
            stateEntries.add(new PieEntry(totalBronzCusNum, getActivity().getResources().getString(R.string.states_bronze)));
            dataColors.add(ContextCompat.getColor(getContext(), R.color.bronze));
        }

        if (totalInStartCusNum != 0) {
            stateEntries.add(new PieEntry(totalInStartCusNum, getActivity().getResources().getString(R.string.states_instart)));
            dataColors.add(ContextCompat.getColor(getContext(), R.color.in_start));
        }

        PieDataSet stateSet = new PieDataSet(stateEntries, null);
        stateSet.setColors(dataColors);
        stateSet.setSliceSpace(2f);
        stateSet.setValueTextSize(14f);
        PieData data = new PieData(stateSet);
        data.setHighlightEnabled(true);
        //set data to view
        statePie.setData(data);
        //style pie chart(view)
        Utility.stylePie(statePie, getActivity().getResources().getString(R.string.no_data_text_customers_states));
        statePie.setCenterText(Utility.doubleFormatter(totalCusNum) + "\n" + getActivity().getResources().getString(R.string.kaseb_dashboard_customer_qty));
        //style legend
        Legend pieLegend = statePie.getLegend();
        Utility.styleLegend(pieLegend);
        //endregion
        //region define sales pie chart
        List<PieEntry> saleEntries = new ArrayList<>();
        List<Integer> saleDataColors = new ArrayList<>();

        if (totalSalesGold != 0) {
            saleEntries.add(new PieEntry(totalSalesGold, getActivity().getResources().getString(R.string.states_gold)));
            saleDataColors.add(ContextCompat.getColor(getContext(), R.color.gold));
        }

        if (totalSalesSilv != 0) {
            saleEntries.add(new PieEntry(totalSalesSilv, getActivity().getResources().getString(R.string.states_silver)));
            saleDataColors.add(ContextCompat.getColor(getContext(), R.color.silver));
        }
        if (totalSalesBronz != 0) {
            saleEntries.add(new PieEntry(totalSalesBronz, getActivity().getResources().getString(R.string.states_bronze)));
            saleDataColors.add(ContextCompat.getColor(getContext(), R.color.bronze));
        }

        if (totalSalesNew != 0) {
            saleEntries.add(new PieEntry(totalSalesNew, getActivity().getResources().getString(R.string.states_instart)));
            saleDataColors.add(ContextCompat.getColor(getContext(), R.color.in_start));
        }


        PieDataSet saleSet = new PieDataSet(saleEntries, null);
        saleSet.setColors(saleDataColors);
        saleSet.setSliceSpace(2f);
        saleSet.setValueTextSize(14f);
        PieData saleData = new PieData(saleSet);
        saleData.setHighlightEnabled(true);

        //set data to view
        salePie.setData(saleData);
        //style pie chart(view)
        Utility.stylePie(salePie, getActivity().getResources().getString(R.string.no_data_text_sale));
        salePie.setCenterText(Utility.formatPurchase(getActivity(), Utility.DecimalSeperation(getActivity(), totalSales)) + "\n" + getActivity().getResources().getString(R.string.kaseb_dashboard_sale_total));
        //style legend
        pieLegend = salePie.getLegend();
        Utility.styleLegend(pieLegend);
        //endregion

        //region define recievables pie chart
        List<PieEntry> recEntries = new ArrayList<>();
        List<Integer> recDataColors = new ArrayList<>();

        if (totalRecGold != 0) {
            recEntries.add(new PieEntry(totalRecGold, getActivity().getResources().getString(R.string.states_gold)));
            recDataColors.add(ContextCompat.getColor(getContext(), R.color.gold));
        }
        if (totalRecSilv != 0) {
            recEntries.add(new PieEntry(totalRecSilv, getActivity().getResources().getString(R.string.states_silver)));
            recDataColors.add(ContextCompat.getColor(getContext(), R.color.silver));

        }
        if (totalRecBronz != 0) {
            recEntries.add(new PieEntry(totalRecBronz, getActivity().getResources().getString(R.string.states_bronze)));
            recDataColors.add(ContextCompat.getColor(getContext(), R.color.bronze));

        }
        if (totalRecNew != 0) {
            recEntries.add(new PieEntry(totalRecNew, getActivity().getResources().getString(R.string.states_instart)));
            recDataColors.add(ContextCompat.getColor(getContext(), R.color.in_start));

        }

        PieDataSet recSet = new PieDataSet(recEntries, null);
        recSet.setColors(recDataColors);
        recSet.setSliceSpace(2f);
        recSet.setValueTextSize(14f);
        PieData recData = new PieData(recSet);
        recData.setHighlightEnabled(true);

        //set data to view
        recPie.setData(recData);
        //style pie chart(view)
        Utility.stylePie(recPie, getActivity().getResources().getString(R.string.no_data_text_sale));
        recPie.setCenterText(Utility.formatPurchase(getActivity(), Utility.DecimalSeperation(getActivity(), totalRec)) + "\n" + getActivity().getResources().getString(R.string.kaseb_dashboard_sale_total));
        //style legend
        pieLegend = recPie.getLegend();
        Utility.styleLegend(pieLegend);
        //endregion

        //endregion

        recievCurs.close();
        customerCurs.close();
        cursor.close();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

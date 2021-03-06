package pivx.org.pivxwallet.ui.node_activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

import global.PivxModule;
import pivx.org.pivxwallet.PivxApplication;
import pivx.org.pivxwallet.R;
import pivx.org.pivxwallet.ui.base.BaseDrawerActivity;
import pivx.org.pivxwallet.ui.base.PivxActivity;
import pivx.org.pivxwallet.ui.transaction_send_activity.IOnFocusListenable;
import pivx.org.pivxwallet.utils.AppConf;
import pivx.org.pivxwallet.utils.DapsController;
import pivx.org.pivxwallet.utils.NavigationUtils;
import pivx.org.pivxwallet.utils.NodeAdapter;
import pivx.org.pivxwallet.utils.NodeInfo;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Neoperol on 5/4/17.
 */

public class NodeActivity extends Fragment implements View.OnClickListener, IOnFocusListenable {
    PivxApplication pivxApplication;
    PivxModule pivxModule;
    DapsController daps;

    private ExpandableListView edit_node;
    private EditText edit_name, edit_host, edit_port, edit_user, edit_password;
    private NodeAdapter nodeAdapter;

    Dialog addNodeDialog;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        pivxApplication = PivxActivity.pivxApplication;
        pivxModule = PivxActivity.pivxModule;
        daps = PivxActivity.daps;

        View root = inflater.inflate(R.layout.fragment_node_settings_new, container, false);
        setupView(root);
        /*View root = inflater.inflate(R.layout.fragment_node_settings, container, false);

        edit_node = (ExpandableListView) root.findViewById(R.id.address_node);
        edit_name = (EditText) root.findViewById(R.id.edit_name);
        edit_host = (EditText) root.findViewById(R.id.edit_host);
        edit_port = (EditText) root.findViewById(R.id.edit_port);
        edit_user = (EditText) root.findViewById(R.id.edit_user);
        edit_password = (EditText) root.findViewById(R.id.edit_password);

        root.findViewById(R.id.btnNodeUpdate).setOnClickListener(this);

        AppConf appConf = pivxApplication.getAppConf();
        NodeActivity.this.edit_name.setText(appConf.getCurNodeInfo().name);
        NodeActivity.this.edit_host.setText(appConf.getCurNodeInfo().host);
        NodeActivity.this.edit_port.setText(String.valueOf(appConf.getCurNodeInfo().port));
        NodeActivity.this.edit_user.setText(appConf.getCurNodeInfo().user);
        NodeActivity.this.edit_password.setText(appConf.getCurNodeInfo().password);*/

        return root;
    }

    private void setupView(View root) {
        final String []types = new String[5];
        types[0] = "35.194.2.105";
        types[1] = "34.69.121.145";
        types[2] = "35.225.119.166";
        types[3] = "34.82.214.16";
        types[4] = "34.83.218.180";

        Spinner nodeSpinner = (Spinner) root.findViewById(R.id.node_spinner);
        final ArrayAdapter<String> nodeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, types);
        nodeSpinner.setAdapter(nodeSpinnerAdapter);
        nodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button addNodeBt = root.findViewById(R.id.button1);
        addNodeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNodeDialog();
            }
        });

        Button dnsDiscoverBt = root.findViewById(R.id.button2);
        dnsDiscoverBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DnsDiscoveryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showAddNodeDialog() {
        addNodeDialog = new Dialog(getActivity());
        addNodeDialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        addNodeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addNodeDialog.setContentView(R.layout.dialog_add_node);
        addNodeDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        addNodeDialog.getWindow().setGravity(Gravity.CENTER);
        addNodeDialog.show();

        Button cancelBt = addNodeDialog.findViewById(R.id.button2);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNodeDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        /*if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        if (nodeAdapter==null) {
            final AppConf appConf = pivxApplication.getAppConf();
            List<String> list = new ArrayList<String>();
            List<NodeInfo> nodeList = appConf.getNodeList();
            for (int i = 0; i < nodeList.size(); i++) {
                list.add(nodeList.get(i).name);
            }

            nodeAdapter= new NodeAdapter(getActivity(), list, appConf.getCurNodeInfo().name);
            edit_node.setAdapter(nodeAdapter);

            edit_node.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    edit_node.getLayoutParams().height = convertDpToPx(90);
                }
            });

            edit_node.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    edit_node.getLayoutParams().height = convertDpToPx(30);
                }
            });

            edit_node.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    NodeAdapter adapter = (NodeAdapter)parent.getExpandableListAdapter();
                    adapter.setText((String)adapter.getChild(groupPosition, childPosition));
                    appConf.setCurNodeIndex(childPosition);

                    List<NodeInfo> list = appConf.getNodeList();
                    NodeActivity.this.edit_name.setText(list.get(childPosition).name);
                    NodeActivity.this.edit_host.setText(list.get(childPosition).host);
                    NodeActivity.this.edit_port.setText(String.valueOf(list.get(childPosition).port));
                    NodeActivity.this.edit_user.setText(list.get(childPosition).user);
                    NodeActivity.this.edit_password.setText(list.get(childPosition).password);

                    parent.collapseGroup(0);
                    return false;
                }
            });
        }*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnNodeUpdate){
            AppConf appConf = pivxApplication.getAppConf();

            String name = this.edit_name.getText().toString();
            String host = this.edit_host.getText().toString();
            int port = Integer.valueOf(this.edit_port.getText().toString());
            String user = this.edit_user.getText().toString();
            String password = this.edit_password.getText().toString();

            appConf.updateCurNode(new NodeInfo(name, host, port, user, password));

            List<String> list = new ArrayList<String>();
            List<NodeInfo> nodeList = appConf.getNodeList();
            for (int i = 0; i < nodeList.size(); i++) {
                list.add(nodeList.get(i).name);
            }

            nodeAdapter.updateData(list, appConf.getCurNodeInfo().name);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public int convertDpToPx(int dp) {
        return (int)(dp * NodeActivity.this.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //edit_node.setIndicatorBounds(edit_node.getWidth()- convertDpToPx(40), edit_node.getWidth());
    }

    private void showErrorDialog(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}

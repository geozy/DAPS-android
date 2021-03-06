package pivx.org.pivxwallet.utils;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import pivtrum.PivtrumPeerData;

import static pivx.org.pivxwallet.module.PivxContext.DEFAULT_RATE_COIN;

/**
 * Created by furszy on 6/8/17.
 */

public class AppConf extends Configurations {

    public static final String PREFERENCE_NAME = "app_conf";
    private static final String IS_APP_INIT = "is_app_init";
    private static final String PINCODE = "pincode";
    private static final String TRUSTED_NODE_HOST = "trusted_node_host";
    private static final String TRUSTED_NODE_TCP = "trusted_node_tcp";
    private static final String TRUSTED_NODE_SSL = "trusted_node_ssl";

    private static final String SELECTED_RATE_COIN = "selected_rate_coin";
    private static final String USER_HAS_BACKUP = "user_has_backup";
    private static final String LAST_BEST_CHAIN_BLOCK_TIME = "last_best_chain_block_time";
    private static final String SPLASH_SOUND = "splash_sound";
    private static final String SHOW_REPORT_ON_START = "show_report";
    private static final String TWOFA= "2fa";
    private static final String TWOFACODE= "2fa_code";
    private static final String TWOFAPERIOD= "2fa_period";
    private static final String TWOFALASTTIME= "2fa_lasttime";

    private List<NodeInfo> nodeList;
    private int curNodeIndex;


    public AppConf(SharedPreferences prefs) {
        super(prefs);

        nodeList = new ArrayList<NodeInfo>();
        curNodeIndex = 0;
    }

    public void setAppInit(boolean v){
        save(IS_APP_INIT,v);
    }

    public boolean isAppInit(){
        return getBoolean(IS_APP_INIT,false);
    }


    public void savePincode(String pincode) {
        save(PINCODE,pincode);
    }

    public String getPincode(){
        return getString(PINCODE,null);
    }

    public void saveTwoFA(String status) {
        save(TWOFA,status);
    }

    public String getTwoFA(){
        return getString(TWOFA,"disabled");
    }

    public void saveTwoFACode(String code) {
        save(TWOFACODE,code);
    }

    public String getTwoFACode(){
        return getString(TWOFACODE,"");
    }

    public void saveTwoFAPeriod(String period) {
        save(TWOFAPERIOD,period);
    }

    public String getTwoFAPeriod(){
        return getString(TWOFAPERIOD,"1");
    }

    public void saveTwoFALastTime(String lasttime) {
        save(TWOFALASTTIME,lasttime);
    }

    public String getTwoFALastTime(){
        return getString(TWOFALASTTIME,"0");
    }

    public void saveTrustedNode(PivtrumPeerData pivtrumPeerData){
        save(TRUSTED_NODE_HOST,pivtrumPeerData.getHost());
        save(TRUSTED_NODE_TCP,pivtrumPeerData.getTcpPort());
        save(TRUSTED_NODE_SSL,pivtrumPeerData.getSslPort());
    }
    public PivtrumPeerData getTrustedNode(){
        String host = getString(TRUSTED_NODE_HOST,null);
        if (host!=null){
            int tcp = getInt(TRUSTED_NODE_TCP,-1);
            int ssl = getInt(TRUSTED_NODE_TCP,-1);
            return new PivtrumPeerData(host,tcp,ssl);
        }else
            return null;
    }

    private void initNodeList() {
//        nodeList.add(new NodeInfo("Node#1", "10.0.2.2", 53573, "bilbo", "baggins"));
        nodeList.add(new NodeInfo("Node#1", "35.194.2.105", 53573, "admin", "admin123"));
        nodeList.add(new NodeInfo("Node#2", "34.69.121.145", 53573, "admin", "admin123"));
        nodeList.add(new NodeInfo("Node#3", "35.225.119.166", 53573, "admin", "admin123"));
        nodeList.add(new NodeInfo("Node#4", "34.82.214.16", 53573, "admin", "admin123"));
        nodeList.add(new NodeInfo("Node#5", "34.83.218.180", 53573, "admin", "admin123"));
    }
    public List<NodeInfo> getNodeList() {
        if (nodeList.size() == 0) {
            initNodeList();
        }

        return nodeList;
    }

    public void updateCurNode(NodeInfo info) {
        nodeList.set(curNodeIndex, info);
    }

    public NodeInfo getCurNodeInfo() {
        if (nodeList.size() == 0) {
            initNodeList();
        }

        return nodeList.get(curNodeIndex);
    }

    public void setCurNodeIndex(int index) {
        curNodeIndex = index;
    }

    public void setSelectedRateCoin(String coin){
        save(SELECTED_RATE_COIN,coin);
    }

    public String getSelectedRateCoin(){
        return getString(SELECTED_RATE_COIN,DEFAULT_RATE_COIN);
    }

    public boolean hasBackup() {
        return getBoolean(USER_HAS_BACKUP,false);
    }

    public void setHasBackup(boolean hasBackup){
        save(USER_HAS_BACKUP,hasBackup);
    }


    public void setLastBestChainBlockTime(long lastBestChainBlockTime) {
        save(LAST_BEST_CHAIN_BLOCK_TIME,lastBestChainBlockTime);
    }

    public long getLastBestChainBlockTime(){
        return getLong(LAST_BEST_CHAIN_BLOCK_TIME,0);
    }

    public void setSplashSound(boolean enabled) {
        save(SPLASH_SOUND,enabled);
    }

    public boolean isSplashSoundEnabled(){
        return getBoolean(SPLASH_SOUND,true);
    }

    public void saveShowReportScreenOnStart(boolean flag) {
        save(SHOW_REPORT_ON_START,flag);
    }

    public boolean getShowReportOnStart(){
        return getBoolean(SHOW_REPORT_ON_START,false);
    }

}

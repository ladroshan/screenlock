package onno.app.lock;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Onno extends Activity {

	DevicePolicyManager dpm;
	ComponentName cn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		cn = new ComponentName(this, Admin.class);
		if(dpm.isAdminActive(cn)){
			dpm.lockNow();
		}
		else {
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            		getString(R.string.admin_explain));
            startActivity(intent);
		}
	}

	public static void disable(Context context)
	{
		DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    	ComponentName cn = new ComponentName(context, Admin.class);
    	if(dpm.isAdminActive(cn))
    		dpm.removeActiveAdmin(cn);
    	System.exit(0);
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.exit(0);
	}

	public static class Admin extends DeviceAdminReceiver {
	    @Override
	    public void onEnabled(Context context, Intent intent) {
	    	DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	    	ComponentName cn = new ComponentName(context, Admin.class);
	    	if(dpm.isAdminActive(cn))
				dpm.lockNow();
	    	System.exit(0);
	    }
	}
}
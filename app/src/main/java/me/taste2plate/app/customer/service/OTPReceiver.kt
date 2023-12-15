package me.taste2plate.app.customer.service

/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
/*
class OTPReceiver : BroadcastReceiver() {
    private var otpReceiveListener: OTPReceiveListener? = null

    fun init(otpReceiveListener: OTPReceiveListener?) {
        this.otpReceiveListener = otpReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("Message", "o")
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras: Bundle? = intent.extras
            val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents
                    val msg: String = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE) as String

                    // extract the 6-digit code from the SMS
                    val smsCode = msg.let { "[0-9]{6}".toRegex().find(it) }
                    smsCode?.value?.let { otpReceiveListener?.onOTPReceived(it) }
                }

                CommonStatusCodes.TIMEOUT -> {
                    otpReceiveListener?.onOTPTimeOut()
                }
            }
        }
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onOTPTimeOut()
    }
}

fun startSMSRetrieverClient(context: Context) {
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsUserConsent(null)
    task.addOnSuccessListener { aVoid ->
        Log.e("Atiar OTP Receiver", "startSMSRetrieverClient addOnSuccessListener $aVoid")
    }
    task.addOnFailureListener { e ->
        Log.e("Atiar OTP Receiver", "startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
    }
}*/

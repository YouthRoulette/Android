package com.kuit.youthroulette.ui.navigation

object Routes {
    const val ONBOARDING = "onboarding"
    const val SIGNUP = "signup"
    const val ROULETTE = "roulette"
    const val BUCKET = "bucket"
    const val RESULT = "result"
    const val PROOF_BASE = "proof"
    const val PROOF = "$PROOF_BASE/{bucketId}"
    const val FRIEND = "friend"
    const val MYPAGE = "mypage"



    fun proof(bucketId: Int): String {
        return "$PROOF_BASE/$bucketId"
    }
}
package com.example.jankenapp//このファイルが所属するパッケージを指定。プロジェクト内での名前空間の役割。

import androidx.compose.runtime.getValue//Compose の by で状態管理する際に必要な delegate 関数のインポート。
import androidx.compose.runtime.mutableStateOf//Composeのbyで状態管理する際に必要なdelegate関数のインポート
import androidx.compose.runtime.setValue//by delegateで状態を変更可能にするために必要。
import androidx.lifecycle.ViewModel//AndroidのViewModelを利用するためのインポート。UIとデータを分離する。


data class JankenUiState(//UIに表示する状態を保持するデータクラス。playerとcpuはそれぞれ手の情報、resultは勝敗の文字列。
    val player: String = "未選択",//プレイヤーの手。初期値は「未選択」
    val cpu: String = "未選択",//CPUの手。初期値は「未選択」
    val result: String = "未対戦",//勝敗結果の初期値は「未対戦」
)

class JankenViewModel : ViewModel() {//ViewModelクラスを定義。UIとロジックを分離し、Composeに状態を提供する。
    var uiState by mutableStateOf(JankenUiState())//UIの状態を保持する変数。mutableStateOfでラップすることで、
    //状態が変わるとComposeが自動で再表画。
        private set//外部からuiStateを書き換えられないようにする。状態の更新はViewModel内の関数のみで行う。
    private val hands = listOf("グー","チョキ","パー")//ジャンケンの手の候補リスト。CPUの手をランダムで選ぶときに使う。

    fun play(playerhand: String) {//プレイヤーが手を出したときに呼ばれる関数引数にプレイヤーの手を受け取る
        val cpuHand = hands.random()//CPUの手をランダムに選ぶ。hands.random()はリストからランダムに1つ返す。
        val result = judge(playerhand, cpuHand)//勝敗を判定する関数を呼び、結果をresultに代入。
        uiState = uiState.copy(//uiStateを更新。copy()を使うことで既存の状態をコピーしつつ一部を書き換える。
            player = playerhand,//プレイヤーの手を更新
            cpu = cpuHand,//CPUの手を更新
            result = result//勝敗結果を更新
        )
    }
    private fun judge(player:String, cpu:String): String {//勝敗を判定する関数。プレイヤーとCPUの手を引数に取り、
        //勝敗を返す
        return when {//条件分岐で勝敗を判定する
            player == cpu -> "あいこ"//プレイヤーとCPUが同じ手ならあいこ
            player == "グー" && cpu == "チョキ" -> "勝ち！"//プレイヤーがグーでCPUがチョキなら勝ち
            player == "チョキ" && cpu == "パー" -> "勝ち！"//プレイヤーがチョキでCPUがパーなら勝ち
            player == "パー" && cpu == "グー" -> "勝ち！"//プレイヤーがパーでCPUがグーなら勝ち
            else -> "負け！"//上記以外は負け。CPUはランダムなことを念頭に置くとわかりやすい
        }
    }

}
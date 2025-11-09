package com.example.jankenapp

import android.os.Bundle//onCreateの引数で使うBundleを利用するためのインポート？
import androidx.activity.ComponentActivity//Activityの基本クラス。Composeを使う場合のベースActivity?
import androidx.activity.compose.setContent//ComposeのUIを画面に表示するための関数setCoountを利用するためのインポート
import androidx.activity.enableEdgeToEdge//画面の端までUIを表示する設定を可能にする関数のインポート
import androidx.compose.foundation.layout.Arrangement//以下のimport群。UIレイアウト・Text・Button・Modifierなど、
import androidx.compose.foundation.layout.Column//Composeのコンポーネントを使うために必要。
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jankenapp.ui.theme.JankenappTheme//作成したテーマ設定を適用するためのインポート
import androidx.lifecycle.viewmodel.compose.viewModel//Compose内でViewModelを取得するための関数、
//viewModel()を使うためのインポート
class MainActivity : ComponentActivity() {//アプリのメイン画面。起動時に最初に表示されるActivity。
    override fun onCreate(savedInstanceState: Bundle?) {//Activityの開始時に1度だけ呼ばれる初期化処理？
        super.onCreate(savedInstanceState)//親クラスの初期化処理を呼び出している。必須？
        enableEdgeToEdge()//画面全体をシステムバー（通知バーなど）を含めて活用する設定。
                setContent {//ComposeのUIを画面に描画する。JankenScreen()がトップ画面として表示される。
                    JankenScreen()
                }
    }
}

@Composable//ComposeのUIを描く関数であることを示すアノテーション
fun JankenScreen(viewModel: JankenViewModel = viewModel()) {//ジャンケン画面のUIを定義する関数。ViewModelを
    //取得して、利用する。
    val state = viewModel.uiState//ViewModelが持つ状態（プレイヤーの手、CPUの手、結果）を取得してUIに反映する。

    Column(//UIを縦方方向に並べるレイアウトコンテナ
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),//画面いっぱいに広げつつ、上下左右に20dpの余白を付ける
        horizontalAlignment = Alignment.CenterHorizontally,//子要素を横方向の中央に配置
        verticalArrangement = Arrangement.Center//子要素を縦方向の中央に配置
    ) {
        Text("あなたの手：${state.player}", fontSize = 20.sp)//プレイヤーの手を表示。ViewModelの状態が変わると
        //自動更新
        Text("相手の手：${state.cpu}",fontSize = 20.sp)//CPUの手を表示
        Spacer(Modifier.height(20.dp))//20dpの空白を入れてレイアウトを整える
        Text("結果：${state.result}",fontSize = 26.sp)//勝敗結果を表示。文字を大きくして強調している。

        Spacer(Modifier.height(40.dp))//テキストとボタンの間に空白を挿入

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)//横並びレイアウト。
        ) {//ボタン間に12dpの間隔を作る。
            Button(onClick = { viewModel.play("グー")}) { Text(text = "グー")}//グーボタン。
            //ViewModelのplay("グー")が実行される。Text(text = "")ボタンの中のテキスト。以下同様。
            Button(onClick = { viewModel.play("チョキ")}) { Text(text = "チョキ")}
            Button(onClick = { viewModel.play("パー")}) {Text(text = "パー")}
        }
    }
}

@Preview(showBackground = true)//Android Studioのプレビュー画面でUIを確認するための設定。　
@Composable//プレビューで描画するUIを定義する。
fun GreetingPreview() {//プレビュー専用の関数名
    JankenappTheme {//テーマを適用した状態でJankenScreenプレビュー表示
        JankenScreen()
    }
}
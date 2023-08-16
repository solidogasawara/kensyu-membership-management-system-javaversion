<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>会員一覧 | 会員管理システム</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/encoding-japanese/2.0.0/encoding.min.js" integrity="sha512-AhAMtLXTbhq+dyODjwnLcSlytykROxgUhR+gDZmRavVCNj6Gjta5l+8TqGAyLZiNsvJhh3J83ElyhU+5dS2OZw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- Select2.css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css">
    <!-- Select2本体 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/MemberSearch.js"></script>
    
    <script type="text/javascript">
        
    </script>
    <script type="text/javascript" src="./js/common.js" defer></script>
</head>
  <body>
    <div id="modal-delete-confirm-window">
        <p>削除しますか？</p>
        <div class="button-box">
            <input class="yes" type="submit" value="はい"" />
            <input class="no" type="button" value="いいえ" />
        </div>
        <input id="modal-member-delete-id" type="hidden" name="id" value="" />
    </div>
    <div class="modal-background"></div>

    <div id="modal-csvupload-window">
        <p>CSVアップロード</p>
        <div class="button-box">
            <input type="file" id="modal-csvupload-file" accept=".csv">
        </div>
        <br>
        <textarea id="modal-csvupload-textarea"></textarea>
        <h2 id="modal-csvupload-done">挿入処理が完了しました</h2>
        <p class="modal-csvupload-result">行 エラー: 件</p>
        <div class="button-box">
            <button class="modal-csvupload-close-button">閉じる</button>
            <button class="modal-csvupload-button">アップロード</button>
        </div>
    </div>    
    <div class="modal-background"></div>

    <div id="modal-searchhint-window">
        <p>ヒント</p>
        <div class="modal-searchhint-result">
            <h2>あいまい検索</h2>
            <p>アスタリスク(*)を利用すると、前方一致、後方一致、部分一致で検索が行えます。</p>
            <ul>
                <li>前方一致</li>
                <p>田中*</p>
                <li>後方一致</li>
                <p>*太郎</p>
                <li>部分一致</li>
                <p>*中太*</p>
                <li>「田」で始まり、「郎」で終わる</li>
                <p>田*郎</p>
            </ul>
            <br>
            <h2>OR検索</h2>
            <p>カンマ(,)を利用すると、OR検索が行えます。</p>
            <ul>
                <li>「田中太郎」と「山田花子」の2名を検索結果に表示する</li>
                <p>田中太郎,山田花子</p>
                <li>あいまい検索との組み合わせ例</li>
                <p>田中*郎,山*子,佐藤*,*一郎</p>
            </ul>
            <br>
        </div>
        <div class="button-box">
            <input class="modal-searchhint-close-button" type="button" value="閉じる">
        </div>
    </div>
    <div class="modal-background"></div>

    <header>
        <h1>会員管理システム</h1>
        <nav>
            <ul>
                <li><a href="member-searh.aspx">会員一覧</a></li>
                <li><a href="member-register.aspx">会員登録</a></li>
                <li><a href="admin-register-page.aspx">システム管理者登録</a></li>
            </ul>
        </nav>
    </header>
    <main>
        <h2>会員一覧</h2>
        
            <table class="search-form">
                <tr>
                    <th>ID</th>
                    <td>
                        
                        <input type="text" name="id" value="" />
                    </td>
                    <th>メールアドレス</th>
                    <td>
                        
                        <input type="text" name="email" value="" />
                    </td>
                </tr>
                <tr>
                    <th>名前</th>
                    <td style="position: relative;">
                        <input type="text" name="name" value="" />
                        <button class="hint-button" onclick="searchHintWindowOpen()">
                            <img src="${pageContext.request.contextPath}/img/01028.png" width="100">
                        </button>
                    </td>
                    <th>名前(かな)</th>
                    <td>
                        
                        <input type="text" name="name_kana" value="" />
                    </td>
                    
                </tr>
                <tr>
                    <th>生年月日</th>
                    <td>
                        <div class="search-input-date-select-input">
                            
                            <input type="date" name="birth-start" value="" min="1950-01-01" max="2025-12-31">
                            <div>～</div>
                            
                            <input type="date" name="birth-end" value="" min="1950-01-01" max="2025-12-31">
                        </div>
                    </td>
                    <th>性別</th>
                    <td>
                        <div class="input-check-list">
                            
                            <label><input type="checkbox" name="sex[]" value="1">男性</label>
                            <label><input type="checkbox" name="sex[]" value="2">女性</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>都道府県</th>
                    <td>
                        <select name="prefecture">
                            <option value=""></option>
                            <option value="1">北海道</option>
                            <option value="2">青森県</option>
                            <option value="3">岩手県</option>
                            <option value="4">宮城県</option>
                            <option value="5">秋田県</option>
                            <option value="6">山形県</option>
                            <option value="7">福島県</option>
                            <option value="8">茨城県</option>
                            <option value="9">栃木県</option>
                            <option value="10">群馬県</option>
                            <option value="11">埼玉県</option>
                            <option value="12">千葉県</option>
                            <option value="13">東京都</option>
                            <option value="14">神奈川県</option>
                            <option value="15">新潟県</option>
                            <option value="16">富山県</option>
                            <option value="17">石川県</option>
                            <option value="18">福井県</option>
                            <option value="19">山梨県</option>
                            <option value="20">長野県</option>
                            <option value="21">岐阜県</option>
                            <option value="22">静岡県</option>
                            <option value="23">愛知県</option>
                            <option value="24">三重県</option>
                            <option value="25">滋賀県</option>
                            <option value="26">京都府</option>
                            <option value="27">大阪府</option>
                            <option value="28">兵庫県</option>
                            <option value="29">奈良県</option>
                            <option value="30">和歌山県</option>
                            <option value="31">鳥取県</option>
                            <option value="32">島根県</option>
                            <option value="33">岡山県</option>
                            <option value="34">広島県</option>
                            <option value="35">山口県</option>
                            <option value="36">徳島県</option>
                            <option value="37">香川県</option>
                            <option value="38">愛媛県</option>
                            <option value="39">高知県</option>
                            <option value="40">福岡県</option>
                            <option value="41">佐賀県</option>
                            <option value="42">長崎県</option>
                            <option value="43">熊本県</option>
                            <option value="44">大分県</option>
                            <option value="45">宮崎県</option>
                            <option value="46">鹿児島県</option>
                            <option value="47">沖縄県</option>
                        </select>
                    </td>
                    <th>会員状態</th>
                    <td>
                        <div class="input-check-list">
                            <label><input type="checkbox" name="member-status[]" value="1">有効</label>
                            <label><input type="checkbox" name="member-status[]" value="2">退会</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div class="button-box">
                              
                            
                            <button class="search-button" onclick="searchButtonClicked();">検索</button>
                        </div>
                    </td>
                </tr>
            </table>
        
        <div class="csv-download-upload">
            <div class="button-box">
                <button class="csv-download-button" onclick="csvDownload(false)">検索結果をCSVダウンロード</button>
                <button class="csv-download-button" onclick="csvDownload(true)">全件CSVダウンロード</button>
                <button id="csv-upload-button" onclick="csvUploadWindowOpen()">CSVアップロード</button>
            </div>
        </div>
        <br />
        <div class="search-list">
          <table id="search-result">
          	<thead>
            <tr id="search-table-header" style="display: none;">
                <th onclick="sortTable('id')">
                    ID 
                    <div id="idSortToggle" style="color: gray; display: inline-block; _display: inline; visibility: visible;">▲</div>
                </th>
                <th>名前</th>
                <th>名前(かな)</th>
                <th>メールアドレス</th>
                <th onclick="sortTable('birthday')">
                    生年月日 
                    <div id="bdSortToggle" style="color: gray; display: inline-block; _display: inline; visibility: hidden;">▲</div>
                </th>
                <th>性別</th>
                <th>都道府県</th>
                <th>会員状態</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        </div>
        <div class="pager">
            <ul>
            </ul>
        </div>
    </main>
         
</body>
</html>
import { Link, Outlet, useNavigate } from "react-router-dom";

function Layout() {
  const navigate = useNavigate();

  const displayName = localStorage.getItem("displayName");

  const logoutHandler = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("userKey");
    localStorage.removeItem("displayName");
    localStorage.removeItem("homepageKey");

    navigate("/");
    window.location.reload();
  };

  return (
    <div style={{ maxWidth: 1000, margin: "0 auto", padding: 32 }}>
      <h1>社内Q&A</h1>

      <nav style={{ display: "flex", gap: 16, marginBottom: 16 }}>
        <Link to="/">ログイン</Link>
        <Link to="/register">ユーザー登録</Link>
        <Link to="/questions">質問一覧</Link>
        <Link to="/questions/new">質問投稿</Link>
        <Link to="/search">検索</Link>
      </nav>

      <div style={{ marginBottom: 32 }}>
        {displayName ? (
          <>
            <span>ログイン中：{displayName}</span>
            <button
              type="button"
              onClick={logoutHandler}
              style={{ marginLeft: 16 }}
            >
              ログアウト
            </button>
          </>
        ) : (
          <span style={{ color: "red" }}>未ログイン</span>
        )}
      </div>

      <Outlet />
    </div>
  );
}

export default Layout;
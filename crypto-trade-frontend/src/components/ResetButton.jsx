import './ResetButton.css'

function ResetButton({ onReset }) {
  const handleClick = async () => {
    if (!window.confirm("Are you sure you want to reset your account?")) return;

    try {
      const res = await fetch("http://localhost:5000/api/account/reset", {
        method: "DELETE"
      });
      const msg = await res.text();
      alert(msg);
      if (onReset) onReset();
    } catch (err) {
      alert("Reset failed: " + err.message);
    }
  };

  return <button className="reset-button" onClick={handleClick}>Reset Account</button>;
}

export default ResetButton;
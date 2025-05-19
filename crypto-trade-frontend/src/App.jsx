import './index.css';
import AccountInfo from './components/AccountInfo';
import Prices from './components/Prices';

function App() {
  return (
    <div style={{ padding: '2rem' }}>
      <h1>Crypto Trading Simulator</h1>
      <AccountInfo />
      <Prices />
    </div>
  );
}

export default App;

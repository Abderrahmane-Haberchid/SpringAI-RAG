
import axios from 'axios'
import './App.css'
import { useState } from 'react';

function App() {

  const [ask, setAsk] = useState('');
  const [response, setResponse] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAsk = async () => {
    setLoading(true);
      await axios.post('http://localhost:8080/api/chat', ask,
        { 
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(response => {
          console.log(response.data);
          setResponse(response.data);
          setLoading(false);
        }).catch(error => {
          console.error('Error:', error);
        });
  }
  

  return (
    <div className='wrapper'>
      <div className='header'>
        <h1>RAG Frontend</h1>
      </div>
      <div className='content'>
        <h1>Ask AI</h1>

        <div className='ask-form'>
          <input type="text" onChange={(e) => setAsk(e.target.value)} />
          <button disabled={loading} 
                  onClick={handleAsk}
                  >
                    {loading ? 'Loading...' : 'Ask'}
          </button>
        </div>

        <div className='response'>
          <h2>Response</h2>
          <p>{response}</p>
        </div>
        
      </div>
    </div>
  )
}

export default App

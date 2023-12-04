import './App.css';
//import QRCodeRenderer from "./Components/QR-Code";
import QRCodeGenerator from "./Components/QR-Code";
//import QRCodeComponent from "./Components/QR-Code";
import React from "react";

// import './App.css';
// import QRCodeGenerator from "./Components/QR-Code";
 import SuccessPage from "./Components/SuccessPage"; // Import the SuccessPage component
// import React from "react";

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <h1>QR Code Generator</h1>
      <Router>
        <Switch>
          <Route exact path="/" component={QRCodeGenerator} />
          <Route exact path="/success" render={(props) => <SuccessPage key={Date.now()} {...props} />} />
          {/* <Route exact path="/success" component={SuccessPage} /> */}
          {/* <Route path="/success" component={SuccessPage} /> */}
        </Switch>
      </Router>
    </div>
  );
}

export default App;


// function App() {
//   return (
//       <div className="App">
//           <h1>QR Code Generator</h1>
//           <QRCodeGenerator/>
//       </div>

      
//   );
// }

// export default App;

//<QRCodeComponent text="Hello, world!" />
// import React, { useEffect, useRef, useState } from "react";

// import QRCode from "react-qr-code";
// import axios from 'axios';


// const QRCodeComponent = (props) => {

//   const { codes } = props;
//   const [qrText, setQRText] = useState("");

//   useEffect(() => {
//     const fetchData = async () => {
//         const result = await axios.post('http://192.168.29.35:8081/qr-generator');
//         console.log(result);
//         setQRText(result.data.qrValue);
//     }
//     fetchData();
//     const interval = setInterval(() => {
//       //  fetchData(); // Fetch data every one minute
//     }, 60000); // 60000 milliseconds = 1 minute
//     return () => clearInterval(interval);
// }, []);

//   return (
//     <div>
//      {/* <QRCode  size={200} value={qrText} /><br></br> */}
//      <QRCode size={200} value={qrText} />
//     </div>
//   );
// };

// export default QRCodeComponent;

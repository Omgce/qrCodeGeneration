  import React, { useState, useEffect } from 'react';
  import QRCode from 'qrcode.react';
  import axios from 'axios';

  //  const QRCodeGenerator = () => {
  //     const [qrText, setQRText] = useState("");
  //     const [intervalId, setIntervalId] = useState(null);
  //     useEffect(() => {
  //         const fetchData = async () => {
  //             try {
  //                 const result = await axios.post('http://192.168.29.35:8081/qr-generator');
  //                 console.log(result);
  //                 setQRText(result.data.qrValue);
  //             } catch (error) {
  //                 console.error('Error while fetching QR code:', error);
  //             }
  //         };
  //         fetchData();
          
  //         const interval = setInterval(() => {
  //           verifyQRCode();
  //           fetchData(); // Fetch data every one minute
  //         }, 5000); // 60000 milliseconds = 1 minute
  //         setIntervalId(intervalId);
  //         return () => clearInterval(interval);

  //     }, []);

  //     const verifyQRCode = async () => {
  //       try {
  //           const verificationResult = await fetch('http://192.168.29.35:8081/qr-verification', {
  //               method: 'POST',
  //               headers: {
  //                   'Content-Type': 'application/json',
  //               },
  //                   });

  //           if (verificationResult.ok) {
  //               const verificationResponse = await verificationResult.json();
  //               console.log(verificationResponse.qrStatus);
  //               if(!verificationResponse.qrStatus){
  //               console.log("User not found.")
  //               }else{
  //               console.log("success.");
  //               clearInterval(intervalId); // Clear the interval
  //              window.location.href = '/success'; 
  //               } 
  //           } else {
  //               console.error('QR code verification failed.');
  //           }
  //       } catch (error) {
  //           console.error('Error while fetching QR code:', error);
  //       }
  //   };
   
  //     return (
  //         <div>
  //             <QRCode size={200} value={qrText} /><br></br>
  //         </div>
  //     );

  // }
  // export default QRCodeGenerator;

const QRCodeGenerator = () => {
  const [qrText, setQRText] = useState("");
  const [verificationResponse, setVerificationResponse] = useState({ qrStatus: false });
  const [intervalId, setIntervalId] = useState(null);

  const fetchData = async () => {
    try {
      const result = await axios.post('http://192.168.29.35:8081/qr-generator');
      console.log(result);
      setQRText(result.data.qrValue);
    } catch (error) {
      console.error('Error while fetching QR code:', error);
    }
  };

  const verifyQRCode = async () => {
    try {
      const verificationResult = await fetch('http://192.168.29.35:8081/qr-verification', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (verificationResult.ok) {
        const data = await verificationResult.json();
        setVerificationResponse(data);
        console.log(data.qrStatus);
        if (data.qrStatus) {
          console.log("Success.");
          clearInterval(intervalId); // Clear the interval
          window.location.href = '/success';
        } else {
          console.log("User not found.");
        }
      } else {
        console.error('QR code verification failed.');
      }
    } catch (error) {
      console.error('Error while fetching QR code:', error);
    }
  };

  useEffect(() => {
    fetchData();
    verifyQRCode(); // Check initially

    const intervalId = setInterval(() => {
      fetchData();
      verifyQRCode();
    }, 5000); // 10000 milliseconds = 10 seconds

    setIntervalId(intervalId);

    return () => {
      clearInterval(intervalId);
    };
  }, []);

  return (
    <div>
      <QRCode size={200} value={qrText} /><br></br>
    </div>
  );
}

export default QRCodeGenerator;


  //  const QRCodeGenerator = () => {
  //     const [qrText, setQRText] = useState("");
  //     const [intervalId, setIntervalId] = useState(null);


  //     useEffect(() => {
  //         const fetchData = async () => {
  //             try {
  //                 const result = await axios.post('http://192.168.29.35:8081/qr-generator');
  //                 console.log(result);
  //                 setQRText(result.data.qrValue);
  //             } catch (error) {
  //                 console.error('Error while fetching QR code:', error);
  //             }
  //         };
  //         fetchData();

  //         const interval = setInterval(() => {
  //           verifyQRCode();
  //           fetchData(); // Fetch data every one minute
  //         }, 10000); // 60000 milliseconds = 1 minute
  //         // Store the interval ID in state
  //         setIntervalId(intervalId);
  //         return () => clearInterval(interval);

  //     }, []);

  //     const verifyQRCode = async () => {
  //       try {
  //           const verificationResult = await fetch('http://192.168.29.35:8081/qr-verification', {
  //               method: 'POST',
  //               headers: {
  //                   'Content-Type': 'application/json',
  //               },
  //                   });

  //           if (verificationResult.ok) {
  //               const verificationResponse = await verificationResult.json();
  //               console.log(verificationResponse.qrStatus);
  //               if(!verificationResponse.qrStatus){
  //               console.log("User not found.")
  //               }else{
  //               console.log("success.");
  //               clearInterval(intervalId); // Clear the interval
  //              window.location.href = '/success'; 
  //               } 
  //           } else {
  //               console.error('QR code verification failed.');
  //           }
  //       } catch (error) {
  //           console.error('Error while fetching QR code:', error);
  //       }
  //   };
   
  //     return (
  //         <div>
  //             <QRCode size={200} value={qrText} /><br></br>
  //         </div>
  //     );

  // }
  // export default QRCodeGenerator;

// const QRCodeGenerator = () => {
//     const [qrText, setQRText] = useState("");
//     const intervalRef = useRef(); // Define interval as a ref
//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 const result = await axios.post('http://192.168.29.35:8081/qr-generator');
//                 console.log(result);
//                 setQRText(result.data.qrValue);
//             } catch (error) {
//                 console.error('Error while fetching QR code:', error);
//             }
//         };
//         fetchData();
//        // verifyQRCode();
//         intervalRef.current = setInterval(() => {
//           verifyQRCode();
//           fetchData(); // Fetch data every one minute
//       }, 60000); // 60000 milliseconds = 1 minute
//       return () => {
//           clearInterval(intervalRef.current); // Clear the interval when the component unmounts
//       };
//     }, []);
//     const verifyQRCode = async () => {
//         try {
//             const verificationResult = await fetch('http://192.168.29.35:8081/qr-verification', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//             });
//             if (verificationResult.ok) {
//                 const verificationResponse = await verificationResult.json();
//                 console.log(verificationResponse.qrStatus);
//                 if (!verificationResponse.qrStatus) {
//                     console.log("User not found.");
//                 } else {
//                     console.log("Success.");
//                     clearInterval(intervalRef.current); 
//                     window.location.href = '/success'; // Redirect to the success page
//                 }
//             } else {
//                 console.error('QR code verification failed.');
//             }
//         } catch (error) {
//             console.error('Error while fetching QR code:', error);
//         }
//     };
//     return (
//         <div>
//             <QRCode size={200} value={qrText} /><br></br>
//         </div>
//     );
// }
// export default QRCodeGenerator;

  
  // const QRCodeGenerator = () => {
  //     const [qrText, setQRText] = useState("");
      
  //     useEffect(() => {
  //         const fetchData = async () => {
  //             try {
  //                 const result = await axios.post('http://192.168.29.35:8081/qr-generator');
  //                 console.log(result);
  //                 setQRText(result.data.qrValue);
  //             } catch (error) {
  //                 console.error('Error while fetching QR code:', error);
  //             }
  //         };
  //         fetchData();
  //         verifyQRCode();

  //         const interval = setInterval(() => {
  //           verifyQRCode();
  //           fetchData(); // Fetch data every one minute
  //         }, 10000); // 60000 milliseconds = 1 minute
  //         return () => clearInterval(interval);
  //     }, []);

  //     const verifyQRCode = async () => {
  //       try {
  //           const verificationResult = await fetch('http://192.168.29.35:8081/qr-verification', {
  //               method: 'POST',
  //               headers: {
  //                   'Content-Type': 'application/json',
  //               },
  //                   });

  //           if (verificationResult.ok) {
  //               const verificationResponse = await verificationResult.json();
  //               console.log(verificationResponse.qrStatus);
  //               if(!verificationResponse.qrStatus){
  //               console.log("User not found.")
  //               }else{
  //               console.log("success.");
  //               clearInterval(interval); // Clear the interval
  //              window.location.href = '/success'; 
  //               } 
  //           } else {
  //               console.error('QR code verification failed.');
  //           }
  //       } catch (error) {
  //           console.error('Error while fetching QR code:', error);
  //       }
  //   };
   
  //     return (
  //         <div>
  //             <QRCode size={200} value={qrText} /><br></br>
  //         </div>
  //     );

  // }

  // export default QRCodeGenerator;



// const QRCodeGenerator = () => {
//     const [qrText, setQRText] = useState("");
//     const history = useHistory();

//     useEffect(() => {
//         const fetchData = async () => {
          
//             const result = await axios.post('http://192.168.29.35:8081/qr-generator');
//             console.log(result);
//             setQRText(result.data.qrValue);
            
//         }
//        // fetchData();

//         const interval = setInterval(() => {
//             fetchData(); // Fetch data every one minute
//         }, 5000); // 60000 milliseconds = 1 minute

//         return () => clearInterval(interval);
//     }, []);

   

//     return (
//         <div>
//             <QRCode size={200} value={qrText} /><br></br>
//             {/* No need for a button, verification is automatic */}
//         </div>
//     );
// };

// export default QRCodeGenerator;

// const QRCodeGenerator = () => {
//     const [qrText, setQRText] = useState("");
//     const history = useHistory();

//     useEffect(() => {
//         const fetchData = async () => {
          
//             const result = await axios.post('http://192.168.29.35:8081/qr-generator');
//             console.log(result);
//             setQRText(result.data.qrValue);
            
//             // Automatically call the verification function when QR text is set
//             verifyQRCode(result.data.qrValue);
//         }
//        // fetchData();

//         const interval = setInterval(() => {
//             fetchData(); // Fetch data every one minute
//         }, 60000); // 60000 milliseconds = 1 minute

//         return () => clearInterval(interval);
//     }, []);

//     // Function to verify the QR code and redirect to another page upon success
//     const verifyQRCode = async (qrToken) => {
//         try {
//             const response = await axios.post('http://192.168.29.35:8081/qr-verification', {
//                 qrToken, // Pass the QR text as the qrToken
//                 deviceId: "deviceId2"
//             });

//             console.log(qrText);
//             console.log(response.data.qrToken);

//             if(response.data.qrToken == qrText ){
//               history.push('/success-page'); 
//             }else{
//               console.error("error");
//             }

//             // Handle the API response here
//            // console.log(response.data);

//             // If the verification is successful, redirect to another page
//             // if (response.data.success) {
//             //     history.push('/success-page'); // Redirect to a success page
//             // }
//         } catch (error) {
//             // Handle errors here (e.g., show an error message)
//             console.error(error);
//         }
//     };

//     return (
//         <div>
//             <QRCode size={200} value={qrText} /><br></br>
//             {/* No need for a button, verification is automatic */}
//         </div>
//     );
// };

// export default QRCodeGenerator;


// const QRCodeGenerator = () => {
//     const [qrText, setQRText] = useState("");
//     const history = useHistory();

//     useEffect(() => {

//         const fetchData = async () => {
//             const result = await axios.post('http://192.168.29.35:8081/qr-generator');
//             console.log(result);
//             setQRText(result.data.qrValue);
//         }
//         fetchData();

//         //  // Automatically call the verification function when QR text is set
//         //  verifyQRCode(result.data.qrValue);

//         const interval = setInterval(() => { 
//           fetchData(); // Fetch data every one minute
//           verifyQRCode();
//         }, 5000); // 60000 milliseconds = 1 minute
//         return () => clearInterval(interval);

//     }, []); 

//     // Function to verify the QR code and redirect to another page upon success
//     const verifyQRCode = async (qrToken) => {
//         try {

//             const response = await axios.post('http://192.168.29.35:8081/qrTokenVerification', {
//                 qrToken,
//                 deviceId: "deviceId"
//             });
//             // Handle the API response here
//             console.log(response.data);

//             // If the verification is successful, redirect to another page
//             // if (response.data.success) {
//             //     history.push('/success'); // Redirect to a success page
//             // }
//         } catch (error) {
//             // Handle errors here (e.g., show an error message)
//             console.error(error);
//         }
//     };

//   //   // Function to verify the QR code and call the API
//   //   const verifyQRCode = async () => {
//   //     try {
//   //         const response = await axios.post('http://192.168.29.35:8081/qr-verification');
//   //         console.log(response);
//   //         // // Handle the API response here (e.g., show a success message)
//   //         // console.log(response.data);
//   //     } catch (error) {
//   //         // Handle errors here (e.g., show an error message)
//   //         console.error(error);
//   //     }
//   // };
    
//     return (
//         <div>
//             <QRCode  size={200} value={qrText} /><br></br>
//         </div>
//     );

// }
// export default QRCodeGenerator;

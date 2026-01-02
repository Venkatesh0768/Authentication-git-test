const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

rl.question('Enter new ngrok URL: ', (url) => {
  const envContent = `VITE_API_BASE_URL=${url}\nVITE_APP_NAME=MyApp\n`;
  
  fs.writeFileSync('.env', envContent);
  fs.writeFileSync('.env.local', envContent);
  
  console.log('✅ Environment files updated!');
  console.log('New URL:', url);
  
  rl.close();
});
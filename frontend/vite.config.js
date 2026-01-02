import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'https://f87f78b36cd1.ngrok-free.app',
        changeOrigin: true,
        secure: false, // Important for ngrok SSL
        rewrite: (path) => path.replace(/^\/api/, '/api')
      }
    }
  }
})
export function ContactPage() {
  return (
    <div className="max-w-2xl mx-auto p-8 bg-white dark:bg-neutral-800 rounded-xl shadow-sm border border-neutral-100 dark:border-neutral-700">
      <h2 className="text-3xl font-bold text-neutral-900 dark:text-white mb-6">
        Get in Touch
      </h2>
      <p className="text-neutral-600 dark:text-neutral-300 mb-8">
        We'd love to hear from you. Please fill out the form below or contact us via email.
      </p>
      
      <form className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Name</label>
          <input type="text" className="w-full p-2 border rounded-lg dark:bg-neutral-900" />
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">Email</label>
          <input type="email" className="w-full p-2 border rounded-lg dark:bg-neutral-900" />
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">Message</label>
          <textarea rows={4} className="w-full p-2 border rounded-lg dark:bg-neutral-900"></textarea>
        </div>
        <button className="w-full bg-primary-600 text-white py-2 rounded-lg font-bold hover:bg-primary-700 transition">
          Send Message
        </button>
      </form>
    </div>
  );
}
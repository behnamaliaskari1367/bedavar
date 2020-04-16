export class LangFa {
  ok = 'تایید';
  cancel = 'لغو';
  search = 'جستجو';
  login = 'ورود';
  edit = 'ویرایش';
  view = 'مشاهده';
  add = 'افزودن';
  delete = 'حذف';
  reset = 'بازنشانی';
}

export class LoginFa extends LangFa {
  pageTitle = 'صفحه ورود';
  username = 'نام کاربری';
  usernameHint = 'لطفا نام کاربری وارد شود';
  password = 'رمز عبور';
  passwordHint = 'لطفا رمز عبور وارد شود';
}

export class AuthorityFa extends LangFa {
  pageTitle = 'اختصاص نقش';
  assigned = 'اختصاص داده شده';
  unAssigned = 'اختصاص داده نشده';
  assign = 'اختصاص';
  revoke = 'لغو';
  name = 'نام';
  type = 'نوع';
  description = 'توضیحات';
}

export class UserFa extends LangFa {
  pageTitle = 'مشخصات کاربر';
  username = 'نام کاربری';
  password = 'رمز عبور';
  firstName = 'نام';
  lastName = 'نام خانوادگی';
  confirmPassword = 'تکرار رمز عبور';
  branch = 'شعبه';
  enabled = 'فعال';
  usernameHint = 'لطفا نام کاربری وارد شود';
  passwordHint = 'لطفا رمز عبور وارد شود';
  samePasswordHint = 'کلمه عبور با تکرار آن یکسان نمی باشد';
  changePassword = 'تغییر رمز';
  assignAuthority = 'تخصیص نقش';
}

export class ChangePasswordFa extends LangFa {
  pageTitle = 'تغییر رمز عبور';
  username = 'نام کاربری';
  password = 'رمز عبور';
  newPassword = 'رمز عبور جدید';
  oldPassword = 'رمز عبور قدیم';
  confirmPassword = 'تکرار رمز عبور';
  usernameHint = 'لطفا نام کاربری وارد شود';
  passwordHint = 'لطفا رمز عبور صحیح وارد شود (حداقل 8 کاراکتر)';
  samePasswordHint = 'کلمه عبور با تکرار آن یکسان نمی باشد';
}

export class HomeFa extends LangFa{
  brand = 'بهداور';
  userInfo = 'مشخصات کاربر';
  resetPassword = 'تغییر رمز';
  about = 'درباره برنامه';
  changeTheme = 'تغییر تم';
  logout = 'خروج';
  samtak = 'سمتاک';
  samat = 'سمات';
  userManagement = 'مدیریت کاربران';
  schedulerService = 'زمانبندی سرویس ها';
}

export class ScheduleParamFa extends LangFa{
  title = 'زمانبندی سرویس';
  serviceName = 'نام سرویس';
  cron = 'زمانبندی';
  enabled = 'فعال';
  url = 'مسیر';
  actionName = 'نام فعالیت';
  invalid = ' اشتباه است ';
  confirmTitle = 'تأیید حذف';
  confirmMessage = 'آیا برای حذف اطمینان دارید؟';
}

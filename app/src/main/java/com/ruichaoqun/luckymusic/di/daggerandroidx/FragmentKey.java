/*
 * Copyright (C) 2017 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain onColorGet copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ruichaoqun.luckymusic.di.daggerandroidx;


import androidx.fragment.app.Fragment;

import java.lang.annotation.Target;

import dagger.MapKey;
import dagger.internal.Beta;

import static java.lang.annotation.ElementType.METHOD;

/** {@link MapKey} annotation to key bindings by onColorGet type of onColorGet {@link Fragment}. */
@Beta
@MapKey
@Target(METHOD)
public @interface FragmentKey {
  Class<? extends Fragment> value();
}
